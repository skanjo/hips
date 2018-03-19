package io.xorshift.hips;

/**
 * @author Samer Kanjo
 * @since 0.3.0 3/19/18 10:4 AM
 */
public final class Version {

  private static final String GROUPID = "${project.groupId}";
  private static final String ARTIFACTID = "${project.artifactId}";
  private static final String VERSION = "${project.version}";
  private static final String REVISION = "${build.revision}";
  private static final String NAME = "${project.name}";

  public static String toBuildString() {
    final String build;
    if (VERSION.contains("SNAPSHOT")) {
      build = NAME + " " + VERSION + " " + REVISION;
    } else {
      build = NAME + " " + VERSION;
    }
    return build;
  }

}
