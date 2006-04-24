package org.apache.maven.plugins.release.config;

/*
 * Copyright 2005-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read and write release configuration and state from a properties file.
 *
 * @author <a href="mailto:brett@apache.org">Brett Porter</a>
 */
public class PropertiesReleaseConfigurationStore
    extends AbstractLogEnabled
    implements ReleaseConfigurationStore
{
    /**
     * The properties file to read and write.
     */
    private File propertiesFile;

    public PropertiesReleaseConfigurationStore()
    {
        // TODO: set properties file somehow
        propertiesFile = new File( "release.properties" );
    }

    public ReleaseConfiguration read( ReleaseConfiguration mergeConfiguration )
        throws ReleaseConfigurationStoreException
    {
        Properties properties = new Properties();

        InputStream inStream = null;
        try
        {
            inStream = new FileInputStream( propertiesFile );

            properties.load( inStream );
        }
        catch ( FileNotFoundException e )
        {
            getLogger().debug( propertiesFile.getName() + " not found - using empty properties" );
        }
        catch ( IOException e )
        {
            throw new ReleaseConfigurationStoreException(
                "Error reading properties file '" + propertiesFile.getName() + "': " + e.getMessage(), e );
        }
        finally
        {
            IOUtil.close( inStream );
        }

        ReleaseConfiguration releaseConfiguration = new ReleaseConfiguration();
        releaseConfiguration.setCompletedPhase( properties.getProperty( "completedPhase" ) );

        // TODO: populate from properties

        if ( mergeConfiguration != null )
        {
            releaseConfiguration.merge( mergeConfiguration );
        }

        return releaseConfiguration;
    }

    public void write( ReleaseConfiguration config )
    {
        // TODO: implement
    }

    public ReleaseConfiguration read()
        throws ReleaseConfigurationStoreException
    {
        return read( null );
    }

    public void setPropertiesFile( File propertiesFile )
    {
        this.propertiesFile = propertiesFile;
    }
}
