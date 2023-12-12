import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String name;

    private String profilePictureLocation;
    private ImageView profilePicture;
    private ArrayList<Profile> friends = new ArrayList<>();
    private String currentStatus = "No current status";


    public Profile(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public ImageView getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePictureLocation) {
        this.profilePictureLocation = profilePictureLocation;
        this.profilePicture = new ImageView(profilePictureLocation);
    }

    public String getProfilePictureLocation() {return this.profilePictureLocation;}

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setFriends(ArrayList<Profile> friends) {
        this.friends = friends;
    }

    public void addFriend(Profile friend) {
        this.friends.add(friend);
    }

    public ArrayList<Profile> getFriends() {
        return this.friends;
    }
}
