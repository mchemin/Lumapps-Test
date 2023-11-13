# Application description

The app present a list of user from api.randomuser.me using the seed lumapps.
When clicking on an user the detail of this user is shown. Either on a new screen or on the left of the current screen if there is enough space.

New item are query when the user scroll to the bottom of the list. If the user do a pull to refresh on the list, we try to refresh the list from start. In case of success, all locally stored item are dropped and replaced by the one we just get. The rest of the list will be refreshed when the user will scroll down.

# Technical choices

## Architecture

The app respect the principles of clean architecture with 3 distinct layers :

- domain layer (in the domain package) contains model and use case that ;
- data layer (in the data package) contains the code related to data fetching and data storage ;
- presentation layer contains the code related to presenting data to the user.

The domain layer does not depends on the data or presentation layer. This two layer depends on the domain layer.

I could have gone a step further, and better separate the network and database, in order to have a cleaner implementation of this.

I also create a separate module name Design. This module contains a theme and some component used to build the app. In a project this size (and with only one module), it is not necessarily, but in a bigger project, with separate feature module, it would be useful.

This Design module also contains a composable for loading image. Placing this composable in a library module allow me to have a standard way of loading images and to not expose the image loading everywhere.

## Lib choices

### Anvil

I choose to use Anvil for the DI.
The DI related code is under the di package.

I have an application component, and two subcomponent (one for the list screen and the other for the detail screen).

The two subcomponents might be over-engineering for an application this size, but if the app grows, having separate scope could be useful.

### Paging 3

In order to achieve the infinite scroll required, i choose to use the paging library from androidx jetpack.
It has a lot of built-in feature to make the display, loading & refreshing of the list manageable.

### Jetpack compose & navigation

As i was starting fresh, i wanted to do a full-compose application, including the navigation. This was an opportunity to use up-to-date approach for android ui development.


### Other

I use other librairies, including :

-  retrofit with moshi for networking, because they do what was needed without much trouble.
- coil for loading image, because it works nicely with jetpack compose.


# What I did not do

### KMM

I consider using KMM for this project, but i was not sure of the strategy to use for implementing infinite loading for iOS. Hence, i prefer due to limited time and my lack of knowledge of iOS development to avoid KMM and enjoy android paging.

### Tests

I did not write tests in this projects.
Because I lack recent experience with UI tests framework like esspresso, i focus on building the app, and the let these tests apart.
I do write some unit tests for my domain layer.
I could also have written tests for the data layer, especially the UserRemoteMediator. This would have been possible because of the dependency inversion principles is used.

### Detekt

I did not take time to setup detekt (or comparable tools) to properly enforce format rules. This would have be a nice tool to have, if the projects was bigger or longer.

### CI/CD

Setting up a CI pipeline to ensure everything is going fine would have been a huge help for a real project.

In the same manner setting up a CD pipeline would help a lot for testing and distributing a real life application.
