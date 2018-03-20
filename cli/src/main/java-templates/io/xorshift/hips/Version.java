package io.xorshift.hips;

/**
 * @author Samer Kanjo
 * @since 0.3.0 3/19/18 10:4 AM
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
