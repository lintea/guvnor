/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.guvnor.common.services.project.backend.server;

import java.net.URL;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.jboss.weld.environment.se.StartMain;
import org.junit.Before;
import org.junit.Test;
import org.uberfire.java.nio.fs.file.SimpleFileSystemProvider;
import org.guvnor.common.services.project.model.Package;
import org.guvnor.common.services.project.service.ProjectService;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.backend.vfs.Path;

import static org.junit.Assert.*;

public class ProjectServiceImplResolvePackageInvalidNoPOMTest {

    private final SimpleFileSystemProvider fs = new SimpleFileSystemProvider();
    private BeanManager beanManager;
    private Paths paths;

    @Before
    public void setUp() throws Exception {
        //Bootstrap WELD container
        StartMain startMain = new StartMain( new String[ 0 ] );
        beanManager = startMain.go().getBeanManager();

        //Instantiate Paths used in tests for Path conversion
        final Bean pathsBean = (Bean) beanManager.getBeans( Paths.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( pathsBean );
        paths = (Paths) beanManager.getReference( pathsBean,
                                                  Paths.class,
                                                  cc );

        //Ensure URLs use the default:// scheme
        fs.forceAsDefault();
    }

    @Test
    public void testProjectServiceInstantiation() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );
        assertNotNull( projectService );
    }

    @Test
    public void testResolvePackageWithNonProjectPath() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL testUrl = this.getClass().getResource( "/" );
        final org.uberfire.java.nio.file.Path testNioPath = fs.getPath( testUrl.toURI() );
        final Path testPath = paths.convert( testNioPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( testPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageWithRootPath() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( rootPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageWithSrcPath() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( rootPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageWithMainPath() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( rootPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageDefaultJava() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/java" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        final URL testUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/java" );
        final org.uberfire.java.nio.file.Path nioTestPath = fs.getPath( testUrl.toURI() );
        final Path testPath = paths.convert( nioTestPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( testPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageDefaultResources() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/resources" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        final URL testUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/resources" );
        final org.uberfire.java.nio.file.Path nioTestPath = fs.getPath( testUrl.toURI() );
        final Path testPath = paths.convert( nioTestPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( testPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageWithJavaFileInDefaultPackage() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/java" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        final URL testUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/java/Bean.java" );
        final org.uberfire.java.nio.file.Path nioTestPath = fs.getPath( testUrl.toURI() );
        final Path testPath = paths.convert( nioTestPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( testPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageWithJavaFileInSubPackage() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/java/org/kie/test" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        final URL testUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/java/org/kie/test/Bean.java" );
        final org.uberfire.java.nio.file.Path nioTestPath = fs.getPath( testUrl.toURI() );
        final Path testPath = paths.convert( nioTestPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( testPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageWithResourcesFileInDefaultPackage() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/resources" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        final URL testUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/resources/rule1.drl" );
        final org.uberfire.java.nio.file.Path nioTestPath = fs.getPath( testUrl.toURI() );
        final Path testPath = paths.convert( nioTestPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( testPath );
        assertNull( result );
    }

    @Test
    public void testResolvePackageWithResourcesFileInSubPackage() throws Exception {

        final Bean projectServiceBean = (Bean) beanManager.getBeans( ProjectService.class ).iterator().next();
        final CreationalContext cc = beanManager.createCreationalContext( projectServiceBean );
        final ProjectService projectService = (ProjectService) beanManager.getReference( projectServiceBean,
                                                                                         ProjectService.class,
                                                                                         cc );

        final URL rootUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/resources/org/kie/test" );
        final org.uberfire.java.nio.file.Path nioRootPath = fs.getPath( rootUrl.toURI() );
        final Path rootPath = paths.convert( nioRootPath );

        final URL testUrl = this.getClass().getResource( "/ProjectBackendTestProjectStructureInvalidNoPOM/src/main/resources/org/kie/test/rule1.drl" );
        final org.uberfire.java.nio.file.Path nioTestPath = fs.getPath( testUrl.toURI() );
        final Path testPath = paths.convert( nioTestPath );

        //Test a non-Project Path resolves to null
        final Package result = projectService.resolvePackage( testPath );
        assertNull( result );
    }

}
