# LudoMemoria

## Description
LudoMemoria is a mobile friendly app designed for gamers. 
It allows for users to create their own library of games so that tracking game progress becomes easier.
Users may also review games and read and vote on the reviews of others.

## Features

### MVP
1. User registration / login & basic profile
2. Users can create and browse their own game library
    - Personal game library with game info (basic details, reviews, etc.)
    - Personal wishlist (Links to game page, cheapest available offer)
    - Personal notes & goals/achievements for each game
3. Browse a library of >600K games
    - GameDB API access via 'IGDB'
    - Detail page for each game (Images, Links, etc.)
    - Links to each game detail/review page

### Should haves:
1. 3rd party API integration
    - SteamAPI for OAuth & autofill personal game library
2. Extended game details
    - Link to game/shop website
    - Platform/System Info
    - Images & Videos
3. PWA, create an installable PWA to allow an easy access, offline library and profile sync for all devices

### Nice to haves
1. Besides voting on reviews, users can open a reddit-like discussion and speak about their opinions
2. Users can share their library or single games progress on social media
3. Community moderation
    - Extended use of platform rewards points
    - More points = more rights ('more vote power', flag offensive reviews for review)
    - Even more points = even more rights (Flag for deletion - 5 delete flags delete review.)

## Techstack / Frameworks
- IDE:
    - IntelliJ
- Backend:
    - Java Spring boot & security
- Frontend:
    - TypeScript React
- Database:
    - MongoDB Atlas
- Deployment:
    - GitHub Actions
    - Heroku
