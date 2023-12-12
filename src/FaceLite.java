import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FaceLite extends Application {

    // Program variables
    Profile profileShown;
    SocialNetwork socialNetwork = new SocialNetwork();

    // North pane components
    Label name = new Label("Name");
    TextField nameField = new TextField();
    Button add = new Button("Add");
    Button delete = new Button("Delete");
    Button lookup = new Button("Lookup");

    // West pane components
    TextField changeStatusField = new TextField();
    TextField changePictureField = new TextField();
    TextField addFriendField = new TextField();
    Button changeStatus = new Button("Change Status");
    Button changePicture = new Button("Change Picture");
    Button addFriend = new Button("Add Friend");

    // Central pane components
    Label username = new Label();
    Label noImageMassage = new Label("No Image");
    Label currentStatus = new Label("No current status");
    Label friendsListHeader = new Label("Friends:");
    Label programMassage = new Label();
    Rectangle square = new Rectangle(240, 240, Paint.valueOf("White"));
    ImageView profilePicture = new ImageView("/resources/KFUPM.png");
    StackPane stackPane = new StackPane(square, noImageMassage);
    VBox friendsList = new VBox(10, friendsListHeader);


    private void addStyleClassToAll(Node[] nodes, String[] names) {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].getStyleClass().add(names[i]);
        }
    }

    private void setComponentsVisibility(boolean isVisible, String... components) {
        for (String component : components) {
            switch (component) {
                case "ImageView" -> profilePicture.setVisible(isVisible);
                case "StackPane" -> stackPane.setVisible(isVisible);
                case "UserInfo" -> {
                    username.setVisible(isVisible);
                    currentStatus.setVisible(isVisible);
                    friendsList.setVisible(isVisible);
                }
                case "Massage" -> programMassage.setVisible(isVisible);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        //
        socialNetwork.load(new File("C:\\Users\\muali\\Downloads\\ics108 project\\src\\Resources\\Users.txt"));
        // North pane action handlers
        add.setOnAction(new AddProfile());
        delete.setOnAction(new DeleteProfile());
        lookup.setOnAction(new LookupProfile());

        // West pane action handlers
        changeStatus.setOnAction(new ChangeStatus());
        changeStatusField.setOnAction(new ChangeStatus());
        changePicture.setOnAction(new ChangePicture());
        changePictureField.setOnAction(new ChangePicture());
        addFriend.setOnAction(new AddFriend());
        addFriendField.setOnAction(new AddFriend());

        // Spacing regions
        Region space1 = new Region();
        Region space2 = new Region();

        // Profile picture resizing to fit the square
        profilePicture.setFitHeight(315);
        profilePicture.setFitWidth(315);

        // Hide the central pane
        setComponentsVisibility(false, "ImageView", "StackPane", "UserInfo");

        // Positioning central pane components
        stackPane.setTranslateX(20);
        stackPane.setTranslateY(90);
        profilePicture.setTranslateX(-17);
        profilePicture.setTranslateY(53);
        username.setTranslateX(25);
        username.setTranslateY(40);
        currentStatus.setTranslateX(25);
        currentStatus.setTranslateY(350);
        friendsList.setTranslateX(350);
        friendsList.setTranslateY(73);
        programMassage.setTranslateX(180);
        programMassage.setTranslateY(510);

        // Creating panes for each region
        HBox northPane = new HBox(20, name, nameField, add, delete, lookup);
        VBox westPane = new VBox(10, changeStatusField, changeStatus, space1, changePictureField, changePicture, space2, addFriendField, addFriend);
        Pane centerPane = new Pane(username, stackPane, currentStatus, friendsList, programMassage, profilePicture);

        // Adding panes to main border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(northPane);
        borderPane.setLeft(westPane);
        borderPane.setCenter(centerPane);

        // Creating and styling scene
        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add("/resources/styles.css");
        addStyleClassToAll(new Node[]{name, add, delete, lookup, changeStatus, changePicture, addFriend, space1, space2, username, square, noImageMassage, currentStatus, profilePicture, friendsListHeader, friendsList, programMassage, westPane, northPane, centerPane},
                new String[]{"name", "add", "delete", "lookup", "change-status", "change-picture", "add-friend", "space", "space", "username", "square", "no-image-massage", "current-status", "profile-picture", "friends-list-header", "friends-list", "program-massage", "west-pane", "north-pane", "center-pane"});
        // Setting stage
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new StoreUsers());
        primaryStage.setTitle("FaceLite");
        primaryStage.show();
    }


    private class AddFriend implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // handle add friend action
            if (profileShown == null) {
                programMassage.setText("Please select a profile to add a friend");
                return;
            }
            String name = addFriendField.getText();
            Profile friend = socialNetwork.lookupProfile(name);
            if (friend == null) {
                programMassage.setText("A profile with the name " + name + " does not exist");
                return;
            } else if (profileShown.getFriends().contains(friend)) {
                programMassage.setText(name + " is already added to your friends list");
                return;
            } else if (friend == profileShown) {
                programMassage.setText("You cannot add yourself as a friend.");
                return;
            }
            socialNetwork.addFriend(profileShown.getName(), name);
            friendsList.getChildren().add(new Label(name));
            programMassage.setText(name + " added as a friend");
        }
    }

    private class ChangeStatus implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // handle change status action
            if (profileShown == null) {
                programMassage.setText("Please select a profile to change status");
                return;
            }
            String status = changeStatusField.getText();
            if (status.isEmpty()) {
                programMassage.setText("Please write a text to use as a status");
                return;
            }
            profileShown.setCurrentStatus(status);
            currentStatus.setText(profileShown.getName() + " is " + status);
            programMassage.setText("Status updated to: " + status);
        }
    }

    private class ChangePicture implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (profileShown == null) {
                programMassage.setText("Please select a profile to change status");
                return;
            }
            try {
                String location = "/resources/" + changePictureField.getText();
                Image image = new Image(location);
                profilePicture.setImage(image);
                profileShown.setProfilePicture("/resources/" + changePictureField.getText());
                profilePicture.setVisible(true);
                if (stackPane.isVisible())
                    stackPane.setVisible(false);
                programMassage.setText("Picture updated");
            } catch (IllegalArgumentException e) {
                programMassage.setText("Image not found");
            }

        }
    }

    private class AddProfile implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // handle add action
            String name = nameField.getText();
            if (name.isEmpty())
                programMassage.setText("Invalid Name, must contain a word");
            else if (socialNetwork.lookupProfile(name) != null)
                programMassage.setText("The username is reserved.");
            else {
                Profile profile = new Profile(name);
                profileShown = profile;
                friendsList.getChildren().setAll(friendsListHeader);
                socialNetwork.addProfile(profile);
                username.setText(name);
                programMassage.setText("New profile created");
                setComponentsVisibility(true, "StackPane", "UserInfo");
                profilePicture.setVisible(false);
            }
        }
    }

    private class DeleteProfile implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // handle delete action
            String name = nameField.getText();
            Profile profile = socialNetwork.lookupProfile(name);
            if (profile == null) {
                programMassage.setText("Profile not found");
                return;
            }
            if (profile == profileShown)
                setComponentsVisibility(false, "StackPane", "ImageView", "UserInfo");
            profileShown = null;
            socialNetwork.deleteProfile(name);
            programMassage.setText("Profile of " + name + " has been deleted");
        }
    }

    private class LookupProfile implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // handle lookup action
            String name = nameField.getText();
            Profile profile = socialNetwork.lookupProfile(name);
            if (profile == null) {
                programMassage.setText("Profile not found");
                return;
            }
            username.setText(name);
            currentStatus.setText(profile.getCurrentStatus());
            programMassage.setText("Displaying " + name);
            friendsList.getChildren().setAll(friendsListHeader);
            for (Profile friend : profile.getFriends())
                friendsList.getChildren().add(new Label(friend.getName()));
            ImageView userProfilePicture = profile.getProfilePicture();
            if (profileShown == null)
                setComponentsVisibility(true, "UserInfo");
            profileShown = profile;
            if (userProfilePicture == null) {
                stackPane.setVisible(true);
                profilePicture.setVisible(false);
            } else {
                profilePicture.setImage(userProfilePicture.getImage());
                profilePicture.setVisible(true);
                stackPane.setVisible(false);
            }
        }
    }

    private class StoreUsers implements EventHandler<WindowEvent>  {
        @Override
        public void handle(WindowEvent event) {
            // handle lookup action
            try (PrintWriter writer = new PrintWriter("C:\\Users\\muali\\Downloads\\ics108 project\\src\\Resources\\Users.txt")) {
                ArrayList<Profile> profiles = socialNetwork.getProfiles();

                for (Profile profile: profiles) {
                    writer.print(profile.getName() + ";" + profile.getProfilePictureLocation() + ";");
                    for (Profile friend: profile.getFriends()) writer.print(friend.getName() + ",");
                    writer.println(";" + profile.getCurrentStatus());
                }

            }
            catch (FileNotFoundException e) {System.out.print(e.getMessage());}
        }
    }
}