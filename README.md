Features : 
  1. Show the list of high schools in NYC
  2. Show the score details of a single school when selected 
  
 Data Source :
  1. https://data.cityofnewyork.us/Education/2017-DOE-High-School-Directory/s3k6-pzi2
  2. https://data.cityofnewyork.us/Education/2012-SAT-Results/f9bf-2cp4
  
 Architecture : MVVM
 
Working video (covering different cases) :
1. Speed scroll
2. Network connectivity
3. Device rotation

https://drive.google.com/file/d/10FEhjP9H-Ipj2gycHR_-PaCd75EoHQSd/view?usp=sharing

Implementation TLDR : 

1. On app start fetch the first batch of items.  
2. On scroll, we fetch the next batch of items. This is achieved by using Paging 3 Library of the architecture components.
3. During the item fetch, we also fetch score details for each item in the list asynchornously in the background.
4. This is to enable a smooth user experience.
5. The score details are stored in a LRU cache, so it is updated as required.
6. App handles cases where there is no internet on start or emply list of items from the api or lost connectivity during anytime.

Things that i would have covered if i had more time
1. Unit tests
2. Edge case of retry on first call failure (explained in code todo)
3. Proper error messages

