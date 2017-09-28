package com.thehellings.config;

import org.ini4j.Ini;

import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wraps access to the configuration ini file to allow defaults to be processed.
 * <p>
 *     Pass the constructor the name of the environment/section that is used. It will handle reading the values from the
 *     named section in an ini file. If no such value is found in that section, it will test for a section named
 *     "defaults" and look there for the value instead.
 * </p>
 * <p>
 *     If there is no section called "defaults", then the fallback functionality is ignored, and only values specified
 *     in the configured section are read. This allows an entire system to be configured properly once, and only values
 *     that change between environments - such as database names and credentials - need to be overridden in the per
 *     environment section.
 * </p>
 * <p>
 *     Reading down through the series of sections is done automatically. Currently there is no process to specify a
 *     multiple level of hierarchy, nor is there a way to prevent a value from being read out of the default section if
 *     no such value is found in the environment section. Perhaps future enhancements will support that, but at present
 *     the author has no use for those functions in his own work.
 * </p>
 */
public class Config {
    private Ini ini;
    private Map<String, String> section;
    private Map<String, String> defaults;

    private static final Logger log = Logger.getLogger(Config.class.getName());
    public static final String DEFAULT_SECTION_NAME = "default";

	/**
     * Reads the value of {@link Enum#name()} as the name for the config section
	 *
	 * <p>
	 *     Passes through name to {@link #Config(String)}
	 * </p>
	 *
     * @param e The name of the environment
	 * @see #Config(String)
     */
    public Config(final Enum e) {
        this(e.name());
    }

	/**
	 * String version of {@link #Config(Enum)}
	 *
	 * <p>
	 *     Defaults the source to be read as a file at path "/config.ini" from the
	 *     class's bundled resources
	 * </p>
	 *
	 * @param environment Name of the environment
	 * @see #Config(Enum)
	 */
	public Config(final String environment) {
        this(environment, Config.class.getResourceAsStream("/config.ini"));
    }

	/**
	 * Reads the value of {@link Enum#name()} as the name for the config section
	 *
	 * @param e Name of the environment
	 * @param stream Place from whence to read the INI file
	 * @see #Config(String, InputStream)
	 */
	public Config(final Enum e, InputStream stream) {
		this(e.name(), stream);
	}

	/**
	 * Reads the specified environment, with fallback, from the specified INI file
	 *
	 * @param environment Name of the environment
	 * @param stream Place from whence to read the INI file
	 */
    public Config(final String environment, InputStream stream) {
		try {
			this.ini = new Ini();
			this.ini.load(stream);
			this.section = this.ini.get(environment);
			this.defaults = this.ini.get(DEFAULT_SECTION_NAME);
		} catch(Exception ex) {
			log.log(Level.SEVERE, "Config could not be opened.");
			ex.printStackTrace();
		}
	}

	/**
	 * Get the effective value of a config file entry with the name provided
	 * <p>
	 *     Looks first in the named section for a value matching that variable. If none is found, it will check in the
	 *     ini section named "defaults". Barring finding an entry there, it will return null.
	 * </p>
	 *
	 * @param name Name of the variable to find
	 * @return The string value of the variable from the configured section, from the defaults section, or null if not found
	 */
    public String get(final String name) {
        if ( this.section != null && this.section.containsKey(name) ) {
            return this.section.get(name);
        }
        if ( this.defaults != null && this.defaults.containsKey(name) ) {
            return this.defaults.get(name);
        }
        return null;
    }

	/**
	 * Reads the value of {@link Enum#name()} as the field name
	 *
	 * @param e The name of the field to read
	 * @return The string value of the variable from the configured section
	 * @see #get(String)
	 */
    public String get(final Enum e) {
        return this.get(e.name());
    }
}
