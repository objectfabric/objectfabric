/// 
/// Copyright (c) ObjectFabric Inc. All rights reserved.
///
/// This file is part of ObjectFabric (objectfabric.org).
///
/// ObjectFabric is licensed under the Apache License, Version 2.0, the terms
/// which may be found at http://www.apache.org/licenses/LICENSE-2.0.html.
///
/// This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
/// WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
/// 

namespace ObjectFabric.Generator
{
    public class ClassDef : org.objectfabric.ClassDef
    {
        public ClassDef()
        {
        }

        public ClassDef( string name )
            : base( name )
        {
        }

        public ClassDef( string name, string description )
            : base( name, description )
        {
        }
    }
}