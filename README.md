# Crypto Portfolio Tracker (CDC)

## Features
- [x] import exported CDC report from csv file
- [x] store report and transactions in Postgres db
- [x] prevent importing the same report multiple times
- [ ] calculate total invested money
- [ ] calculate total invested money per asset
- [ ] calculate all time profit and loss
- [ ] calculate all time profit and loss per asset


## Running the application locally


1. Create Postgres Docker container
    
    ```bash
    docker run --name crypto-tracker-db -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgres
    ```
   
2. Connect to your Postgres container and create db with name `portfolio-tracker`
    
3. Run the application (it will generate schema/tables)

    ```bash
    ./mvnw spring-boot:run
    
    ```
    
4. Import your CDC csv file using the `/cdc/import` endpoint

5. Do whatever you want... tbc... 