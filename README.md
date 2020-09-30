# Fast & Furious Showtime

## Notes
There are a few compromises in this implementation. The first
is that I gave up on having JPA manage the relationships
between entities. Instead, I added that logic manually. I realize
this is not ideal, but I found myself struggling far too much
with the framework, so at least for this exercise, it was a compromise
I was willing to make.

I opted for a naively simple security mechanism for limiting
access to creating show times. I realize something like
Spring Security would be more... Secure - but it seemed
overkill, and as I was already having more trouble with
the framework than I'd have liked - decided not to add
another thing into the mix.

I did not test-drive this application, which I would 
normally do, but again my lack of expertise with
Spring frustrated my efforts. Additionally, the
test coverage is less than total, and I opted instead 
to demonstrate proficiency in the concept of testing.

Finally, I chose to implement in Java after a couple
false-starts with Kotlin which made me feel like I
was fighting with Spring a lot more than I thought
I should be.

Finally, the instructions suggested adding anything
I thought would be useful. I did not do this for several
reasons - not the least of which is the time I spent
fighting with the Spring framework. In any case
I would not have added features I thought were useful
because I've essentially been trained not to. Over
the years I've generally adopted the YAGNI principle
in order to focus effort on the most important and
needed features. That does not mean however that I
don't think about what would be a good addition, and
I will suggest, debate and argue for features I think
are good but I won't typically add things that are
not the 'next most important thing'.

After this long-winded explanation about my philosophy
on the relationship between Engineering and Product,
some features I think would be useful for an application
like this would be:

 * The ability to buy tickets
 * The ability to reserve particular seats
 * Count of reviews by Star Count (X 5-Star, Y 4-Star, etc)
 * The ability for the cinema owner to add new movies 
 (if for example a new F&F movie is released)
 * The ability for the cinema owner to see in real-time
 their revenue in terms of tickets sold
 * The ability to see historical ticket sales for each movie 

## Configuration
Create an `application.yaml` file in the root directory, in it
create the following keys
```
security:
  api-key: "[Whatever you'd like]"

omdb:
  api-key: "[The key you got from https://www.omdbapi.com/]"

```

You don't need to configure an external database, as we're
using H2 in process. 

## Start the application
This will start the application in the foreground
`./mvnw spring-boot:run`

## Swagger
You can find the Swagger UI for this api [here](`http://localhost:8080/swagger-ui/index.html`)

## API Examples
I've added some formatting with `jq`, which you can
install (on OSX with homebrew installed) using
`brew install jq`

Alternatively, you can pipe output to `python -m json.tool` 

### All Movies
```
curl "http://localhost:8080/movies" | jq
```

## Movie 1
```
curl "http://localhost:8080/movies/1" | jq
```

## Movie 99 (Doesn't exist)
This produces a lot of output, but the headers show
the response code is 404
```
curl -v "http://localhost:8080/movies/99" | jq
```

## Create Showtime
Run several times, you will see the id increase. If you change
the movie id in the url to a non-existing one, you'll get a 
Not-Found response.
```
curl -X "POST" "http://localhost:8080/movies/1/showtimes?apiKey=[PUT YOUR KEY HERE]" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -v \
     -d $'{
  "ticketPrice": 11.99,
  "startTime": "12:34"
}' | jq
```

## Movie 1 Showtimes
```
curl "http://localhost:8080/movies/1/showtimes" | jq
```

## Movie 1 Reviews
```
curl "http://localhost:8080/movies/1/reviews" | jq
```
## New Review
```
curl -X "POST" "http://localhost:8080/movies/1/reviews" \
     -H 'Content-Type: application/json; charset=utf-8' \
     -d $'{
  "email": "foo@bar.baz",
  "stars": 5,
  "review": "This was great!",
  "name": "Foo Bar"
}'
```