import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SocialNetwork {
    private ArrayList<Profile> profiles;

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public SocialNetwork() {
        profiles = new ArrayList<>();
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }

    public void deleteProfile(String name) {
        // Remove the profile from all friend lists
        for (Profile profile: profiles) {
            profile.getFriends().remove(lookupProfile(name));
        }
        // Delete the profile from the profiles list
        profiles.remove(lookupProfile(name));
    }

    public Profile lookupProfile(String name) {
        for(Profile profile : profiles) {
            if(profile.getName().equals(name)) {
                return profile;
            }
        }
        return null;
    }

    public void addFriend(String name1, String name2) {
        Profile profile1 = lookupProfile(name1);
        Profile profile2 = lookupProfile(name2);
        if (profile1 != null && profile2 != null) {
            profile1.addFriend(profile2);
            profile2.addFriend(profile1);
        }
    }

    public void load(File profiles) throws FileNotFoundException {
        Scanner reader = new Scanner(profiles);
        while (reader.hasNextLine()) {
            this.profiles.add(new Profile(reader.nextLine().split(";")[0]));
        }
        reader.close();

        reader = new Scanner(profiles);
        while (reader.hasNextLine()) {
            String[] content = reader.nextLine().split(";");
            Profile profile = lookupProfile(content[0]);
            if (!content[1].equals("null"))
                profile.setProfilePicture(content[1]);
            if (!content[2].equals("null"))
                profile.setFriends(getFriendsProfiles(content[2].split(",")));
            profile.setCurrentStatus(content[3]);
        }
        reader.close();
    }

    public ArrayList<Profile> getFriendsProfiles(String[] names) {
        ArrayList<Profile> friends = new ArrayList<>();
        for (String name: names) {
            Profile friend = lookupProfile(name);
            if (friend != null)
                friends.add(friend);
        }
        return friends;
    }
}