java -cp route.jar  org.durian.route.RouteMain mumbai bangalore route2.csv
java -cp route.jar  org.durian.route.RouteMain mumbai margao india.csv

java -cp route.jar  org.durian.route.RouteMain mumbai margao india.csv
City: [mumbai]
City: [margao]
City: [mangalore]
City: [madikeri]
City: [virajpet]
City: [kannur]
City: [bangalore]
City: [mysore]
mumbai-(BY_TRAIN)-margao
mumbai-(BY_CAR)-margao
margao-(BY_TRAIN)-mangalore
margao-(BY_CAR)-mangalore
mangalore-(BY_BUS)-madikeri
madikeri-(BY_BUS)-virajpet
virajpet-(BY_BUS)-kannur
virajpet-(BY_BUS)-bangalore
mysore-(BY_TRAIN)-bangalore
mysore-(BY_TRAIN)-mangalore
graph G {
# neato -Tpng tst.dot -o foo.png
mumbai -- margao [label="765"];
mumbai -- margao [label="765"];
margao -- mangalore [label="430"];
margao -- mangalore [label="430"];
mangalore -- madikeri [label="155"];
madikeri -- virajpet [label="35"];
virajpet -- kannur [label="60"];
virajpet -- bangalore [label="260"];
mysore -- bangalore [label="139"];
mysore -- mangalore [label="308"];
}
Reading: constraints.txt
-> NoDirectReturn
-> MaximumSize,12
Reading: endconstraints.txt
Routes: 1
Added: 2
-- 2,2
Routes: 2
GOOD: R[mumbai-margao]:      0 SEK, 12:00, 765 km. mumbai->BY_TRAIN->margao
GOOD: R[mumbai-margao]:      0 SEK, 11:00, 765 km. mumbai->BY_CAR->margao
Added: 0
-- 2,2
ms: 180
Size: 2
R[mumbai-margao]:      0 SEK, 12:00, 765 km. mumbai->BY_TRAIN->margao
R[mumbai-margao]:      0 SEK, 11:00, 765 km. mumbai->BY_CAR->margao

java -cp route.jar  org.durian.route.RouteMain mumbai bangalore route2.csv
...
ms: 472
Size: 1835

Shortest time:
R[mumbai-bangalore]:      0 SEK, 18:00, 1642 km. mumbai->BY_PLANE->margao->BY_CAR->mangalore->BY_TRAIN->mysore->BY_TRAIN->bangalore
R[mumbai-bangalore]:      0 SEK, 19:00, 1642 km. mumbai->BY_PLANE->margao->BY_TRAIN->mangalore->BY_TRAIN->mysore->BY_TRAIN->bangalore
R[mumbai-bangalore]:      0 SEK, 19:00, 1645 km. mumbai->BY_PLANE->margao->BY_CAR->mangalore->BY_BUS->madikeri->BY_BUS->virajpet->BY_BUS->bangalore

Least connexions:
R[mumbai-bangalore]:      0 SEK, 30:00, 1559 km. mumbai->BY_CAR->mangalore->BY_TRAIN->mysore->BY_TRAIN->bangalore
R[mumbai-bangalore]:      0 SEK, 18:00, 1642 km. mumbai->BY_PLANE->margao->BY_CAR->mangalore->BY_TRAIN->mysore->BY_TRAIN->bangalore
R[mumbai-bangalore]:      0 SEK, 19:00, 1642 km. mumbai->BY_PLANE->margao->BY_TRAIN->mangalore->BY_TRAIN->mysore->BY_TRAIN->bangalore

Shortest distance:
R[mumbai-bangalore]:      0 SEK, 30:00, 1559 km. mumbai->BY_CAR->mangalore->BY_TRAIN->mysore->BY_TRAIN->bangalore
R[mumbai-bangalore]:      0 SEK, 31:00, 1562 km. mumbai->BY_CAR->mangalore->BY_BUS->madikeri->BY_BUS->virajpet->BY_BUS->bangalore
R[mumbai-bangalore]:      0 SEK, 18:00, 1642
km. mumbai->BY_PLANE->margao->BY_CAR->mangalore->BY_TRAIN->mysore->BY_TRAIN->bangalore
