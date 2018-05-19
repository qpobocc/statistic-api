# statistic-api
The main use case for our API is to calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is
called every time a transaction is made. It is also the sole input of this rest API. The other one returns the statistic based of the transactions of the last 60 seconds.

## Specs

### POST /transactions

Every Time a new transaction happened, this endpoint will be called.

#### Request sample
```http
{
    "amount": 12.3,
    "timestamp": 1478192204000
}
```
Where:
* amount is double specifying transaction amount
* timestamp is epoch in millis in UTC time zone specifying transaction time

Returns: Empty body with either 201 or 204.
* 201 - in case of success
* 204 - if transaction is older than 60 seconds


### GET /statistics
This is the main endpoint of this task, this endpoint have to execute in constant time
and memory (O(1)). It returns the statistic based on the transactions which happened
in the last 60 seconds.

#### Response sample
```http
{
    "sum": 1000,
    "avg": 100,
    "max": 200,
    "min": 50,
    "count": 10
}
```

Where:
* sum is a double specifying the total sum of transaction value in the last 60
seconds
* avg is a double specifying the average amount of transaction value in the last
60 seconds
* max is a double specifying single highest transaction value in the last 60
seconds
* min is a double specifying single lowest transaction value in the last 60
seconds
* count is a long specifying the total number of transactions happened in the last
60 seconds

## Usage

1. Maven build by using following command

    mvn clean install

2. To run application use following command

    java -jar statistic-api-1.0.0.jar
