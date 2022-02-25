# Android Places Search View

<img src='https://img.shields.io/badge/kotlin-%230095D5.svg?&style=for-the-badge&logo=kotlin&logoColor=white' height='25'/><img src='https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white&style=for-the-badge' height='25'/><img src='https://jitpack.io/v/fsfaysalcse/AndroidPlacesView.svg?style=for-the-badge&logo=appveyor' height='25'/>

> A small library that integrating your autocomplete Edittext to the Google Places API.

## Installation

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

```bash
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```bash
  dependencies {
	   implementation 'com.github.fsfaysalcse:AndroidPlacesView:1.0'
	}
```

## Usage

```xml

<AutoCompleteTextView android:id="@+id/autoCompleteEditText" android:layout_width="match_parent"
    android:layout_height="wrap_content" android:background="@drawable/bg_auto_compleate"
    android:hint="@string/type_in_your_location" android:padding="8dp" />

```

```Kotlin
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val placesApi = PlacesView.Builder()
        .apiKey(GOOGLE_MAP_API_KEY)
        .build(this@MainActivity)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.autoCompleteEditText.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
    }
}
```

## For handle item click event

```kotlin
    autoCompleteEditText.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
    val place = parent.getItemAtPosition(position) as Places

}
```

## Getting details of places

```kotlin
   placesApi.getPlaceDetails(placeId, object : OnPlaceDetailsListener {
    override fun onError(errorMessage: String) {

    }

    override fun onSuccess(placeDetails: PlaceDetails) {
        //TODO do anything with placeDetails object
    }
}
)
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would
like to change.

Please make sure to update the tests as appropriate.
