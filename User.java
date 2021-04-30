public class User {
  private int _id;
  private String _name;
  private String _userFileName;

  public String getFilename() {
    return _userFileName;
  }

  public boolean setFilename( String filename ) {
    // TODO: check if filename already exists and return false if it does
    _userFileName = filename;
    return true;
  }
}
