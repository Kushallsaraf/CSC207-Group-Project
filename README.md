# **Game Central**
A clean-architecture game discovery platform with social features and personalized recommendations.

## **Authors & Contributors**
- Taabish Khan 
- Kushall Saraf
- Saad Qureshi 
- Rohan Singh
- Yash Ingale

## Table of Contents
1. [Authors & Contributors](#authors--contributors)
2. [Summary](#summary)
3. [Features](#features)
4. [Game Central User Guide](#game-central-user-guide)
    - [Login](#login)
    - [Important](#important)
    - [Homepage](#homepage)
        - [Recommendations](#recommendations)
        - [Game Search](#game-search)
        - [Navigation](#navigation)
    - [Game Page](#game-page)
    - [Personal Profile Page](#personal-profile-page)
    - [Follow Other Users](#follow-other-users)
5. [Known Issues](#known-issues)
6. [Future Features](#future-features)


## **Summary**
**What:** Game Central is an IMDB-style platform for video games. It allows users to browse games, view details, leave reviews, follow other users, and get personalized recommendations.  
**Why:** Platforms like IMDB and Goodreads have made it easy to find your next movie or book and meet like-minded people, but gaming has yet to produce a satisfactory equivalent.   
**Who:** Gamers who want a one-stop hub for exploring games, sharing reviews, and discovering new titles based on their tastes.



## **Features**
- **User Profiles** — View and edit your profile, see your wishlist and library.
- **Follower System** — Follow other users and see their reviews and activity.
- **Game Search & Details** — Search games by title, view details like genre, developer, release date, platforms, ratings, and cover art.
- **Reviews** — Leave reviews for games you have played; see others’ opinions.
- **Recommendations** — Personalized game suggestions based on your highest-rated reviews, genres, and wishlist.
- **Data Caching** — API responses from IGDB and RAWG are cached via Firebase for performance and offline access.
- **Clean Architecture** — Separation into entities, use-cases, and interface adapters for maintainable and scalable code.



## Game Central User Guide

### Login

To log in, you must first be signed up. In the long run 
we hope to provide seperate views for signup and login, however
presently they are combined into a single view. As such, 
type your intended username and password, hit signup, and then login
with those same credentials in the same fields. 


## Important: 

After logging in, you might be overwhelmed with the sheer amount of options you have. 
As such, we recommend you search for the games Mass Effect 3, The Witcher 3, and Elden Ring, as our team, as users, 
have left reviews under these games. In viewing our reviews, you can click on our usernames to see our profiles. Feel free to follow any one of us. 

### Homepage

#### Recommendations 
Welcome to our homepage. On the bottom you will notice 
a series of game cards. These are games being recommended to you, and you can click on them 
to view their game page. Our program supports
personalized recommendations, however, if it 
is your first time and you have not interacted much with the software, 
your default recommendations will be the best reviewed games. Over time, as you add games to your wishlist, library, 
and reviews, your recommendations will become much more personalized. 

#### Game Search 

In the center of the homepage you will notice a big search bar. Click on it to search
games and users using the search button, or genres using the search by genre button. 
If your search shows results, feel free to add games to your wishlist 
or your library. Additionally, you may click on the game to view 
its page, containing detailed information about it, where you will have the 
chance to leave a review on it as well. 

#### Navigation

You might notice the navigation bar on top of the homepage, this bar will allow 
you to navigate to your personal profile, or check out news about 
gaming on the news page. 


### Game Page 

If you click on a game you will be on its game page. You 
can use this page to get more information on the game, and you can 
also leave reviews under it. You can also add the game to your library or wishlist
as well on that page. 


### Personal Profile Page

If you click on library in the top right corner, you can see your personal profile. This is where your library and wishlist will show up, giving you the option 
of removing games from your lists. You can also add a profile picture by pasting a photo url 
upon clicking edit, during which you can change your bio too. You can click on followers or following to see the users on those lists. 

### Follow Other Users 

You can search for other users on the homepage search bar. If you don't any users, 
try searching Gandalf, taabish, saad, or Yash. You can click on the result, and follow (or unfollow)
them. 

## Known Issues 

- edit reviews button not working 
- popups make full screen inconvenient when editing personal profile or viewing followers 
- Game reviews on homepage sometimes show as zero 
- clicking on gaming news opens a window, which is inconveneint for full screen users
- GUI does not scale well with smaller screen sizes 
- Genre search is very specific and unrefined
- Search bar in nav is not functional 


## Future Features

- Change password
- Shorter loading times 
- Logout button 
- Improved genre search 
- Advanced search function 






 

