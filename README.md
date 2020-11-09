We tackled the issue of finding intelligent ways for managing properties and bringing awareness to the intricacies of real estate all over Finland. Through our solution, we give access to multiple granularities of data, capable of assisting investors, analysts, and clients at the same time.
Smart Property Management - Team RootKiskacsa
Motivation
As outlined by the challenge, the intricacies of real estate in Finland makes managing, searching for, and understanding real estate difficult. During our research, we concluded that the best way to solve these issues is through a standardized framework containing multiple granularities of data, capable of assisting investors, analysts, and clients at the same time.

Overview of the Approach
We propose a multi-hierarchy approach utilizing machine learning, statistical approaches, and seamless engineering. The advantage of an integrated, hierarchical solution is showing different levels of data in the same context - resulting in a methodology that is both easy to use and to understand.

Our first statistical hierarchy showcases the growth of different districts in constructing and renovating real estates through a color-gradient based visualization. To attain these results, we used Helsinki's data API and calculated the compound average growth rates from district-based construction and renovation data. Through machine learning (linear regression), we also created forecasts on how this will change in the upcoming years - predicting the increase or decrease in growth.

Built atop of this, to emphasize the ecological footprint of Finland, we created and visualized eco-indexes of districts based on Energy, Heat, and Water consumption of households within them.

Over these data, specific apartment buildings are shown. A detailed description can be seen of the apartment (gained through OPs dataset). Predictions of these apartments' eco-indexes were calculated through scalably clustering and aggregating already known eco-indices.
Additionally, specific diagrams are shown to better understand the (dis-)advantages of the apartment.

Through our solution, investors can locate fast- and slow-growing areas, potential customers can access specific apartment data, and analysts - through our API - have a free hand to use and extend our work.

Overview of the Implementation
On the technical side, our approach utilizes the data provided by the organizers through a re-usable pipeline - which we data engineered to hell - and is
hosted through google cloud platform through a PostgreSQL database and a Spring boot middleware to our JavaScript-based frontend.
We provide a flexible public API for data scientists, to freely contribute to, and to do any kind of their magic using it.

LOCATION
Through Brella: https://next.brella.io/events/connected/people?profile=593410
VIDEO
https://www.youtube.com/watch?v=vz2jIcD-g1M

DEMO
http://35.205.22.186/
