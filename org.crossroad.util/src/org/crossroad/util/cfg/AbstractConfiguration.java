/**
 * 
 */
package org.crossroad.util.cfg;

import java.io.FileInputStream;
import java.util.Properties;

import javax.naming.ConfigurationException;

import org.crossroad.util.log.AbstractLogger;

/**
 * @author e.soden
 *
 */
public  abstract class AbstractConfiguration extends AbstractLogger implements IConfiguration{
	private Properties prp = null;
	private boolean loaded = false;
	/**
	 * 
	 */
	public AbstractConfiguration(String homeDir) {
		DirHelper.putHomeDir(homeDir);
	}


	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IConfiguration#isLoaded()
	 */
	@Override
	public boolean isLoaded() {
		return loaded;
	}
	
	
	

	public void loadConfiguration() throws ConfigurationException {
		
		try {
			String propfile = DirHelper.getInstance().getConfPath("configuration.properties");

			this.prp = new Properties();
			this.prp.load(new FileInputStream(propfile));

			customLoad();
			
			this.loaded = true;
		} catch (Exception e) {
			this.loaded = false;
			log.error(e);
		} finally {
			
		}
		
	}
	
	protected abstract void customLoad() throws Exception;
	

	protected Properties getProperties()
	{
		return this.prp;
	}

	
}
