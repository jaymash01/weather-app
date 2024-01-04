package com.jaymash.weatherapp.controllers;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jaymash.weatherapp.App;
import com.jaymash.weatherapp.dialogs.MessageDialog;
import com.jaymash.weatherapp.models.City;
import com.jaymash.weatherapp.models.Preferences;
import com.jaymash.weatherapp.utils.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsController implements Initializable {

    private App app;
    private Stage stage;

    @FXML
    private TextField city;

    @FXML
    private ScrollPane cityList;

    @FXML
    private RadioButton unitsMetric;

    @FXML
    private RadioButton unitsImperial;

    @FXML
    private ImageView indexLink;

    private Preferences preferences;

    public void setApp(App app) {
        this.app = app;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void save() {
        try {
            File file = new File(getClass().getResource("/preferences.txt").toURI());
            String toWrite = new Gson().toJson(preferences);
            FileUtils.writeToFile(file, toWrite);
            app.gotoIndex();
        } catch (Exception ex) {
            new MessageDialog(stage, "Error", ex.getMessage()).show();
        }
    }

    public void initialize() {
        indexLink.setOnMouseClicked((MouseEvent ev) -> app.gotoIndex());
        preferences = app.getPreferences();

        if (preferences != null) {
            city.setText(preferences.getCity().getName());

            String units = preferences.getUnits();
            if (units.equals("metric")) {
                unitsMetric.setSelected(true);
            } else {
                unitsImperial.setSelected(true);
            }
        } else {
            preferences = new Preferences();
        }

        unitsMetric.setOnMouseClicked((MouseEvent ev) -> {
            if (unitsMetric.isSelected()) {
                unitsImperial.setSelected(false);
                unitsMetric.setSelected(true);
            }

            preferences.setUnits("metric");
        });

        unitsImperial.setOnMouseClicked((MouseEvent ev) -> {
            if (unitsImperial.isSelected()) {
                unitsMetric.setSelected(false);
                unitsImperial.setSelected(true);
            }

            preferences.setUnits("imperial");
        });

        setupCitySearch();
    }

    private void setupCitySearch() {
        try (InputStream inputStream = getClass().getResourceAsStream("/tz.city.list.json")) {
            String content = FileUtils.readFromInputStream(inputStream);
            JsonArray cities = new Gson().fromJson(content, JsonArray.class);

            city.setOnKeyReleased((KeyEvent ev) -> {
                List<City> matchedCities = new ArrayList<>();
                String keywords = city.getText().toLowerCase();
                int i = 0;

                for (; i < cities.size(); i++) {
                    JsonObject jsonObject = (JsonObject) cities.get(i);
                    if (jsonObject.get("name").getAsString().toLowerCase().contains(keywords)) {
                        // only show a maximum of 10 suggestions
                        if (matchedCities.size() <= 10) {
                            City city1 = new City();
                            city1.setId(jsonObject.get("id").getAsInt());
                            city1.setName(jsonObject.get("name").getAsString());
                            city1.setCountry(jsonObject.get("country").getAsString());
                            matchedCities.add(city1);
                        }
                    }
                }

                if (keywords.trim().isEmpty()) {
                    cityList.setVisible(false);
                } else {
                    if (!matchedCities.isEmpty()) {
                        cityList.setVisible(true);

                        VBox cityListItemsRoot = new VBox();

                        for (City city1 : matchedCities) {
                            HBox cityListItem = new HBox();
                            cityListItem.getStyleClass().add("city-list-item");
                            Text cityName = new Text(city1.getName());
                            cityName.getStyleClass().add("text");
                            cityListItem.getChildren().add(cityName);
                            cityListItemsRoot.getChildren().add(cityListItem);
                            cityList.setContent(cityListItemsRoot);

                            cityListItem.setOnMouseClicked((MouseEvent ev1) -> {
                                preferences.setCity(city1);
                                city.setText(city1.getName());
                                cityList.setVisible(false);
                            });
                        }
                    } else {
                        cityList.setVisible(false);
                    }
                }
            });
        } catch (Exception ex) {
            new MessageDialog(stage, "Error", ex.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resBundle) {
        cityList.setVisible(false);
    }

}
