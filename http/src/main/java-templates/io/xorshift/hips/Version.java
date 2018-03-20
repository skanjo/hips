package io.xorshift.hips;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 2:39 PM
 */
public class Version {

  private static final Version VERSION = new Version();

  public static String buildString() {
    return VERSION.toString();
  }

  Version() {
  }

  String version() {
    return "${project.version}";
  }

  String revision() {
    return "${build.revision}";
  }

  String name() {
    return "${project.name}";
  }

  public String toString() {
    final String build;
    if (version().contains("SNAPSHOT")) {
      build = name() + " " + version() + " " + revision();
    } else {
      build = name() + " " + version();
    }
    return build;
  }

}
