# genderImbalanceInMovies
What does it take for a movie to be feminist?
Defining feminism in Hollywood 
Janice Preko, Moji Niyamanusorn, and Hana Nagata
We would like to thank our professors and our teaching assistants for helping us with this project.

INTRODUCTION
This project serves to investigate and analyze the issue of gender imbalance in Hollywood movies. We will analyze these imbalances through quantitative tests using a feminist score which determines just how great the extents of these imbalances are in movies, or lack of.  The test for a feminist movie has been implemented as follows.
The feminist score combines several existing tests by reputable individuals in order to determine whether the movie is feminist – namely the Rees-Davies test, the Ko test, the Pierce test, and the Hagen test. Overall, the feminist score that we implement looks at the gender breakdowns behind the camera (Rees-Davies), the presence of a female protagonist (Pierce), the gender breakdown of the supporting cast (Hagen), and diversity in front of the camera (Ko).
The highest priority was given to the test that looked at gender behind the scenes, as we believe diversity behind the camera translates to women creating movies that include more women and represent in the best light. Although four different tests were included to create this feminist score, there were instances where the scores of different movies overlapped. To break these ties and to further look at how feminist these movies were, we created another criterion – one which looks at how many of the 13 tests the movie passed. The more tests passed, the higher their ranking in the feminist movie rank list would be. If there proved to still be further ties, then alphabetical order was used in giving priority to the movie.

METHOD
The FiveThirtyEight article “Creating the New Bechdel Test” divided the Bechdel-alternative tests into four sub-categories. We felt that it was appropriate to select a test from each sub-category to be representative of what should be valued in the score. Four selected tests are the Rees-Davies test (tests that look behind the camera), the Ko test (tests that look beyond white women), the Pierce test (tests that look at female protagonists), and the Hagen test (tests that look at the supporting cast). To calculate the feminist score (feministScore() method), we weighed the score of each test based on each sub-category’s importance. The order is 1) tests that look behind the camera (+4 score), 2) tests that look at female protagonists (+3 score), 3) tests that look at the supporting cast (+2 score), and 4) tests that look at the supporting cast (+1 score). In feministScore(), if a movie passes a test, a score is added to the movie’s total feminist score. The higher the score, the more “feminist” the movie is. However, as many movies pass the same number of tests, the total number of tests (out of 13 tests) that it passed is added as a fifth criterion.
The rankMovies() method then ranks the score based on 4 criteria in the feministScore() method, using the compareTo() method we defined in Movie class. If two movies receive the same feminist score, the fifth criterion is used to break the tie (movies that pass more tests in total will therefore be ranked higher). The rankMovies() method returns PriorityQueue<Movie>, which uses LinkedMaxHeap to store data. Thus, the toString() method of PriorityQueue follows MaxHeap’s convention, returning elements in inorder traversal order. However, LinkedMaxHeap only ensures that the element at the root is the one with the highest priority, but not necessarily the ranking of the rest of the nodes.  For users to see an accurate ranking, they must use the dequeue() method until the PriorityQueue becomes empty. Every time the PriorityQueue got dequeued, the tree structure of LinkedMaxHeap reorganizes itself to ensure that the element at the root is always the highest priority (has the highest feminist score out of the movies left in the tree). 
If more movies were included in the data set, the solution would definitely change. The more movies there are, the higher chance that more movies will have the same score, even with the fifth criterion provided. More criteria should be added to increase the dispersion of the score. 

CONCLUSIONS
From our definition of the feminist score, we allocated four points for passing the Rees-Davies test, three points for passing the Pierce test, two points for passing the Hagen test, and one point for passing the Ko test. Therefore, the highest possible feminist score that a movie can have was ten. Based on how few movies were passing the thirteen tests in the “Creating the New Bechdel Test” article, we did not expect many movies to be “feminist” according to our definition, especially since our definition of a feminist score depended on four tests that looked at different aspects of the film.
Unsurprisingly, the majority of the movies did not have high feminist scores – the most “feminist” movie based on our definition was Finding Dory with nine points, whereas the least “feminist” movies were Sully, Teenage Mutant Ninja Turtles: Out of the Shadows, and Deadpool, each scoring zero points. Out of the three movies that scored the lowest, our tie-breaker (the total number of tests that each Movie passes) indicated that Deadpool was the least “feminist”, passing zero out of the four tests we chose, and passing only one out of all thirteen tests. The rest of the results were as follows (from most “feminist” to least “feminist”):
TITLE (FEMINIST_SCORE,TOTAL_NUMBER_OF_TESTS_PASSED)
Finding Dory (9,6)
Sing (8,5)
Sausage Party (8,4)
Suicide Squad (8,4)
Star Trek Beyond (8,3)
Alice Through the Looking Glass (7,4)
Storks (7,4)
Batman v Superman: Dawn of Justice (7,3)
Captain America: Civil War (7,3)
Jason Bourne (7,2)
Bad Moms (6,8)
Ice Age: Collision Course (5,5)
Kung Fu Panda 3 (5,5)
Hidden Figures (4,7)
Independence Day: Resurgence (4,7)
Ghostbusters (4,6)
Boo! A Madea Halloween (4,5)
The Divergent Series: Allegiant (4,5)
Fantastic Beasts and Where to Find Them (4,4)
Now You See Me 2 (4,4)
Pete's Dragon (4,4)
The Purge: Election Year (4,4)
Central Intelligence (4,3)
Hacksaw Ridge (4,3)
Moana (4,3)
Ride Along 2 (4,3)
The Accountant (4,2)
Doctor Strange (4,1)
Arrival (3,5)
Miss Peregrine's Home for Peculiar Children (3,5)
The Boss (3,5)
The Girl on the Train (3,5)
La La Land (3,4)
Passengers (3,4)
The Conjuring 2: The Enfield Poltergeist (3,4)
X-Men: Apocalypse (3,4)
10 Cloverfield Lane (3,3)
Don't Breathe (3,3)
Lights Out (3,3)
The Magnificent Seven (3,3)
Trolls (3,3)
Zootopia (3,3)
Rogue One: A Star Wars Story (3,2)
The Legend of Tarzan (3,2)
The Secret Life of Pets (3,1)
The Angry Birds Movie (1,3)
The Jungle Book (1,2)
Sully (0,3)
Teenage Mutant Ninja Turtles: Out of the Shadows (0,3)
Deadpool (0,1)

Each line (as described in the first row as well) is structured in the format: title (feminist score, total number of tests passed). The feminist scores were used to compare the movies, and when there were multiple movies with the same feminist scores, the total number of tests passed were then used to create a further priority. For movies that had both the same feminist score and the same number of tests passed, the movies were given priority based on the alphabetical order of their titles.

Figure 1. The distribution of movies depending on their feminist scores.
The majority of the movies scored between 3 and 4 (Figure 1) and calculations revealed that the average feminist score was 4.1. Only 26% of movies held a score of or higher than 5, and 6% of movies did not pass any of the feminist criteria that were defined by us.
Since the four tests we chose to define our feminist score looked at filmmaking holistically (from characters and actors to behind-the-scenes staff), movies that pass all four tests (or even score highly in general) are movies that are “feminist” in more than one area of filmmaking. From the set of data tested here, because none of the 50 movies passed all four tests, one might also say that most of the mainstream Hollywood films that are being produced are far from being truly feminist.
As all the members of our group are international students, we noticed that most tests targeted movies that were produced in English-speaking countries (since they were targeting Hollywood films). If we are to create an alternative Bechdel test to add to the existing thirteen, therefore, it would be one that would be able to target movies from all around the world. Specifically, we want to create a test that would combat the fetishization of foreign women, so our test will be something like “a movie passes if the foreign female characters (relative to where the movie is set in) are not exoticized or fetishized in any way”. While we do not expect a lot of current movies to pass this new test, it would be great if this becomes a new standard that film industries follow.

COLLABORATION
Our group was able to divide the work fairly evenly – although each member had sections that they were responsible for, we ensured that we could reach out and seek help from the other members whenever necessary. We often checked each other’s work and made suggestions where needed. One major aspect that allowed good teamwork was that we maintained clear communication throughout the time of collaboration. 
Specifically, for task 1 (implementation of the Actor and Movie classes), we first had a meeting so that everyone understood the basic gist of the project and how the classes worked together and then implemented the two classes together. For task 2 (implementation of the MovieCollection class), we counted how many methods we needed to write, and divided the work evenly. For task 3, we first each read the “Creating the New Bechdel Test” article in detail and chose which tests mattered to us the most. Then, we discussed together to choose which tests were important for all of us and used those tests to define our feminist score. For task 4 (implementation of the PriorityQueue class), we made sure that we all understood what the underlying mechanisms were before implementing them. Finally, we split up the sections of the report among the three of us so that we could efficiently finish the project. 

