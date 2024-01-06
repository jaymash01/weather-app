## Weather App

This is a desktop weather application developed using JavaFX. To run the project, follow the instructions provided at [https://openjfx.io/openjfx-docs/](https://openjfx.io/openjfx-docs).

The app uses Weather API from [OpenWeatherMap](https://openweathermap.org/api) to get current weather data and 5 day / 3 hour forecast. You'll have to get an API key and run the app with a commandline argument which is the key you got.

## Packaging
The Maven plugin ModiTect is used as a tool to help the packaging process go smooth. For more information about the plugin, please visit [https://github.com/moditect/moditect](https://github.com/moditect/moditect).

After packaging, copy the <code>preferences.json</code> file to the <code>bin</code> folder of your <code>jlink-image</code>.
