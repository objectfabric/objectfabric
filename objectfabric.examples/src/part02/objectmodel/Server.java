/**
 * Copyright (c) ObjectFabric Inc. All rights reserved.
 *
 * This file is part of ObjectFabric (objectfabric.com).
 *
 * ObjectFabric is licensed under the Apache License, Version 2.0, the terms
 * of which may be found at http://www.apache.org/licenses/LICENSE-2.0.html.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package part02.objectmodel;

import org.junit.Assert;
import org.junit.Test;

import part02.objectmodel.generated.Car;
import part02.objectmodel.generated.Driver;
import part02.objectmodel.generated.MyObjectModel;
import part02.objectmodel.generated.Settings;
import part03.replication.generated.MyClass;

import com.objectfabric.LazyMap;
import com.objectfabric.TList;
import com.objectfabric.TObject;
import com.objectfabric.TSet;
import com.objectfabric.misc.PlatformAdapter;
import com.objectfabric.misc.PlatformConsole;
import com.objectfabric.misc.SeparateClassLoader;
import com.objectfabric.transports.Server.Callback;
import com.objectfabric.transports.http.HTTP;
import com.objectfabric.transports.socket.SocketConnection;
import com.objectfabric.transports.socket.SocketServer;

/**
 * This example shows how to define a more complex object model. ObjectFabric supports a
 * set of basic types like String, Integer, Date or byte[] (Check ImmutableClass for the
 * full set), and collections like TMap, TSet, and TList. Those collections behave like
 * usual Java collections, but can be transfered, persisted and are transactional (Check
 * part05.stm for more about transactions).
 * <nl>
 * In addition to basic types and collections, objects can be created through code
 * generation. ObjectFabric's code generator allows creation of classes containing fields.
 * Each field can be either of a basic type, a collection, or another generated class. It
 * supports inheritance, read only fields, and a few other features. Check online help for
 * more information.
 * <nl>
 * Overall, all objects supported by ObjectFabric are either simple (immutable) or derive
 * from TObject (Collections and generated objects).
 * <nl>
 * In this sample code is generated by a Java program (Check /generator/Main.java). The
 * generator can also be invoked by command line by executing ObjectFabric's jar file.
 */
public class Server {

    private static final int PORT = 8080;

    public static SocketServer run(boolean waitForEnter) throws Exception {
        /*
         * If your application has a generated object model, you must register it so that
         * your objects can be deserialized. This is always the first line of an
         * ObjectFabric application.
         */
        MyObjectModel.register();

        SocketServer server = new SocketServer(PORT);

        server.setCallback(new Callback<SocketConnection>() {

            public void onConnection(SocketConnection session) {
                System.out.println("Connection from " + session.getRemoteAddress());

                /*
                 * Send objects to client. Objects can be of the types listed in
                 * com.objectfabric.ImmutableClass, collections like
                 * com.objectfabric.TList, or your own generated classes.
                 */

                /*
                 * Send immutable classes.
                 */
                session.send("Blah");
                session.send(42);

                /*
                 * Send collections.
                 */
                TSet<String> set = new TSet<String>();
                set.add("Element");
                session.send(set);
                session.send(new TList<MyClass>());

                /*
                 * Send generated classes.
                 */
                Car car = new Car("Brand");
                Driver owner = new Driver();
                owner.setName("Joe");
                car.setDriver(owner);

                Driver friend = new Driver();
                Settings settings = new Settings();
                settings.setSeatHeight(5);
                car.getSettings().put(friend, settings);

                /*
                 * During serialization of a collection or generated class, the whole
                 * graph of referenced objects will be sent too. Lazy objects like LazyMap
                 * can be sent without their content being serialized.
                 */
                session.send(car);

                /*
                 * Object have unique instances, if an object is sent again it will be
                 * received as the same object. Also, its content will not be sent again.
                 */
                session.send(friend);

                /*
                 * By default an object sent to another process is serialized with all its
                 * references. Content of lazy objects is instead retrieved on demand by
                 * the remote process. Clients can fetch entries one key at a time.
                 */
                LazyMap<String, TObject> map = new LazyMap<String, TObject>();
                map.put("A", new Car("Brand A"));
                map.put("B", new Car("Brand B"));
                session.send(map);
            }

            public void onDisconnection(SocketConnection session, Throwable t) {
                System.out.println("Disconnection from " + session.getRemoteAddress());
            }

            public void onReceived(SocketConnection session, Object object) {
                Assert.assertEquals("Done!", object);

                if (_client != null)
                    _client.interrupt();
            }
        });

        /*
         * This line enables HTTP connections. It is not required for the Java client, but
         * allows connections from the GWT version of this sample.
         */
        server.addFilter(new HTTP());

        /*
         * Start the server.
         */
        server.start();

        if (waitForEnter) {
            System.out.println("Started socket server, press enter to exit.");
            PlatformConsole.readLine();
        }

        return server;
    }

    public static void main(String[] args) throws Exception {
        run(true);
    }

    // Testing purposes

    private static SeparateClassLoader _client;

    @Test
    public void asTest() throws Exception {
        SocketServer server = run(false);

        _client = new SeparateClassLoader(Client.class.getName());
        _client.start();
        _client.join();

        server.stop();
        PlatformAdapter.reset();
    }
}