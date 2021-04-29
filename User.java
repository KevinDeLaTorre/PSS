public class User {
  private int id;
  private String name;
  private String userFileName;

  public String getFilename() {
    return this.userFileName;
  }

  public boolean setFilename( String filename ) {
    // TODO: check if filename already exists and return false if it does
    this.userFileName = filename;
    return true;
  }
}
