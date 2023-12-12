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
import java.util.Map;

public class FaceLite extends Application {

    // Program variables
    Profile profileShown;
    String currentTheme = "White";
    SocialNetwork socialNetwork = new SocialNetwork();

    // North pane components
    Label name = new Label("Name");
    TextField nameField = new TextField();
    Button add = new Button("Add");
    Button delete = new Button("Delete");
    Button lookup = new Button("Lookup");
    Button changeTheme = new Button("Change Theme");

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
    ImageView profilePicture = new ImageView();
    StackPane stackPane = new StackPane(square, noImageMassage);
    VBox friendsList = new VBox(10, friendsListHeader);

    // Title bar components
    Label programName = new Label("FaceLite");
    ImageView closeIcon = new ImageView("/Resources/Icons/Close.png");
    ImageView minimizeIcon = new ImageView("/Resources/Icons/Minimize.png");
    ImageView maximizeIcon = new ImageView("/Resources/Icons/Maximize.png");
    Button close = new Button("", closeIcon);
    Button minimize = new Button("", minimizeIcon);
    Button maximize = new Button("", maximizeIcon);

    // Declaring the scene
    Scene scene;


    private void addStyleClassToAll(Map<Node, String> nodeStyles) {
        for (Map.Entry<Node, String> entry : nodeStyles.entrySet()) {
            entry.getKey().getStyleClass().add(entry.getValue());
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
        // Load the users
        socialNetwork.load(new File("C:\\Users\\muali\\Downloads\\ics108 project\\src\\Resources\\Users.txt"));

        // North pane action handlers
        add.setOnAction(new AddProfile());
        delete.setOnAction(new DeleteProfile());
        lookup.setOnAction(new LookupProfile());
        changeTheme.setOnAction(new ChangeTheme());

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
        Region space3 = new Region();

        // Top bar customization
        space3.setMinWidth(630);

        closeIcon.setFitWidth(25);
        closeIcon.setFitHeight(25);

        minimizeIcon.setFitWidth(25);
        minimizeIcon.setFitHeight(25);

        maximizeIcon.setFitWidth(25);
        maximizeIcon.setFitHeight(25);

        programName.setTranslateX(5);

        // Title bar action handlers
        close.setOnAction(event -> primaryStage.close());
        maximize.setOnAction(event -> primaryStage.setMaximized(!primaryStage.isMaximized()));
        minimize.setOnAction(event -> primaryStage.setIconified(true));

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
        programMassage.setTranslateY(460);

        // Creating panes for each region
        HBox northPane = new HBox(20, name, nameField, add, delete, lookup, changeTheme);
        VBox westPane = new VBox(10, changeStatusField, changeStatus, space1, changePictureField, changePicture, space2, addFriendField, addFriend);
        HBox titleBar = new HBox(programName, space3, minimize, maximize, close);
        Pane centerPane = new Pane(username, stackPane, currentStatus, friendsList, programMassage, profilePicture);

        // Adding panes to main pane
        BorderPane root = new BorderPane();
        root.setTop(new VBox(5, titleBar, northPane));
        root.setLeft(westPane);
        root.setCenter(centerPane);

        // Setting and styling scene
        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("/resources/styles.css");
        addStyleClassToAll(Map.ofEntries(Map.entry(name, "name"), Map.entry(nameField, "name-field"), Map.entry(add, "add"), Map.entry(delete, "delete"), Map.entry(lookup, "lookup"), Map.entry(changeTheme, "change-theme"), Map.entry(changeStatus, "change-status"), Map.entry(changeStatusField, "change-status-field"), Map.entry(changePicture, "change-picture"), Map.entry(changePictureField, "change-picture-field"), Map.entry(addFriend, "add-friend"), Map.entry(addFriendField, "add-friend-field"), Map.entry(space1, "space"), Map.entry(space2, "space"), Map.entry(programName, "program-name"), Map.entry(close, "close-button"), Map.entry(maximize, "maximize-button"), Map.entry(minimize, "minimize-button"), Map.entry(titleBar, "title-bar"), Map.entry(username, "username"), Map.entry(square, "square"), Map.entry(noImageMassage, "no-image-massage"), Map.entry(currentStatus, "current-status"), Map.entry(profilePicture, "profile-picture"), Map.entry(friendsListHeader, "friends-list-header"), Map.entry(friendsList, "friends-list"), Map.entry(programMassage, "program-massage"), Map.entry(westPane, "west-pane"), Map.entry(northPane, "north-pane"), Map.entry(centerPane, "center-pane"), Map.entry(root, "root")));
        // Setting stage
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new StoreUsers());
        primaryStage.initStyle(StageStyle.UNDECORATED);
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
            }
            if (profileShown.getFriends().contains(friend)) {
                programMassage.setText(name + " is already added to your friends list");
                return;
            }
            if (friend == profileShown) {
                programMassage.setText("You cannot add yourself as a friend.");
                return;
            }
            socialNetwork.addFriend(profileShown.getName(), name);
            Label addedFriend = new Label(friend.getName());
            addedFriend.getStyleClass().add("friend");
            friendsList.getChildren().add(addedFriend);
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
                profileShown.setProfilePicture("/resources/" + location);
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
            currentStatus.setText(name + " is " + profile.getCurrentStatus());
            programMassage.setText("Displaying " + name);
            friendsList.getChildren().setAll(friendsListHeader);
            for (Profile friend : profile.getFriends())
                friendsList.getChildren().add(new Label(friend.getName()));
            friendsList.getChildren().forEach(node -> node.getStyleClass().add("friend"));
            ImageView userProfilePicture = profile.getProfilePicture();
            if (profileShown == null)
                setComponentsVisibility(true, "UserInfo");
            profileShown = profile;
            if (userProfilePicture == null) {
                stackPane.setVisible(true);
                profilePicture.setVisible(false);
            }
            else {
                profilePicture.setImage(userProfilePicture.getImage());
                profilePicture.setVisible(true);
                stackPane.setVisible(false);
            }
        }
    }

    private class ChangeTheme implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // handle change theme action
            if (currentTheme.equals("White")) {
                currentTheme = "Dark";
                scene.getStylesheets().remove("/Resources/White-Theme.css");
                scene.getStylesheets().add("/resources/Dark-Theme.css");
            }
            else {
                currentTheme = "White";
                scene.getStylesheets().remove("/resources/Dark-Theme.css");
                scene.getStylesheets().add("/Resources/White-Theme.css");
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