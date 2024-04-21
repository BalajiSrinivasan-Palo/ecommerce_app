# Ecommerce Application

This is a simple ecommerce application built with Java, Spring Boot, and Gradle. It is designed to handle basic ecommerce functionalities such as processing orders and managing product inventory.

## Overview of Dataset

The dataset provided contains several sheets, which describe products and mappings specific to each e-commerce platform:

- **Lazada Sheet**: Contains products directly listed with unique identifiers (product IDs), descriptions, prices, quantities, and a unique entity number (UEN).
- **Shopee Sheet**: Lists products similarly but includes a `ref_pid` that might need mapping to standardize product IDs across platforms.
- **Shopee_map Sheet**: Provides a mapping from `ref_pid` to `ProductID` which helps in resolving any discrepancies in product identification across different systems.
- **Q10 Sheet**: Similar to the Lazada sheet but includes an `Is_delete` column indicating whether a product is active or has been removed from the listing.

## Database Schema Design

The schema designed to accommodate this dataset involves several related tables:

- **Shops Table**: Stores information about each shop or vendor, uniquely identified by a name.
    - Fields: `id` (auto-incremented primary key), `name` (unique)
- **Products Table**: Contains details about each product. Since product IDs are not unique across different companies (shops), the combination of `product_id` and `uen` is used to uniquely identify products.
    - Fields: `id` (auto-incremented primary key), `product_id`, `uen`, `name`, `description`, `shop_id` (foreign key linked to Shops), `quantity`, `price`, `isDeleted` (indicates if the product is active or deleted)
- **Orders Table**: Holds order information. Each order is linked to a shop and can contain multiple products.
    - Fields: `id` (auto-incremented primary key), `order_reference_id` (unique), `status`, `shop_id` (foreign key linked to Shops), `order_date`, `total_price`
- **OrderItems Table**: Each record relates an order to the products it contains, along with quantities and prices specific to that order.
    - Fields: `id` (auto-incremented primary key), `order_id` (foreign key linked to Orders), `product_id` (foreign key linked to Products), `quantity`, `total_price`

## Data Integration Strategy

To integrate the data into this schema:
- **Shop Identification**: Each UEN from the dataset is assumed to correspond to a distinct shop, and these are entered into the Shops table. 
- **Product Entries**: Products from each sheet are inserted into the Products table. For Shopee, the Shopee_map is essential to correctly relate `ref_pid` to `ProductID`, ensuring consistency across product listings.
- **Handling Deletions**: For Q10, the `isDeleted` flag is set based on the `Is_delete` column, allowing the system to retain records of removed products for historical data integrity or potential reactivation.

## Application Design

Key Components:
- **OrderService**: This is the main service class that handles order processing. It interacts with the OrderRepository and ProductRepository to manage orders and update product inventory.
- **ProductService**: This service class is responsible for managing product-related operations, such as updating product stock.
- **NotificationService**: This service class is used to send notifications to users when a product is out-of-stock. This is inferred from the ProductController where the notifySupplyTeam method of NotificationService is called when the quantity of a product is zero.
- **Error Handling**: The application uses a custom error handling mechanism. ApiException is the main exception class that is thrown when an API error occurs. ApiErrorHandler is an enum that implements the ErrorFormat interface and is used to define API error codes and messages.

## Database

The application uses an in-memory database for simplicity. The ProductRepository and OrderRepository interfaces are used to interact with the database.

## Endpoints

The application exposes the following endpoints:
- **/products/item-menu**: GET endpoint to fetch the product details.
- **/products/product_enquiry**: GET endpoint to fetch the product available quantity.
- **/orders**: POST endpoint to process a new order.
- **/orders/{orderReferenceId}**: GET endpoint to retrieve the status of an order.
- **/orders/{orderReferenceId}**: DELETE endpoint to cancel an order.


## Cloning the Github repository

To run the application, follow these steps:
1. **Clone the Repository**:
   Clone the repository to your local machine using the following command:
   ```bash
   git clone  
   
2. **Navigate to the Project Directory**:
   Change the directory to the project folder:
   ```bash
   cd ecommerce
    ```
## Docker Compose Configuration

The [docker-compose.yml](docker-compose.yml)`docker-compose.yml` file defines the following services:

- **app**: Builds the Spring Boot application image and exposes port `8080`.
- **mysql**: Uses the MySQL 5.7 image, sets up the MySQL database, and exposes port `3307`.

## Usage Instructions

1. **Prerequisites**:
  - Docker and Docker Compose installed on your system.

2. **Configuration**:
  - Open `docker-compose.yml`.
  - Override `SPRING_JPA_HIBERNATE_DDL_AUTO` with an `none` to disable schema initialization.
  - 
3. **Build and Run**:
  - Open a terminal and navigate to the directory containing `docker-compose.yml`.
  - Run `docker-compose up --build` to build and start the containers.

4. **Accessing the Application**:
  - Once containers are running, access the Spring Boot app at `http://localhost:8080`.

5. **Stopping the Containers**:
  - To stop the containers, run `docker-compose down` in the terminal.

6. **Customization**:
  - Modify the Docker Compose file to suit your project's requirements.
  - Update environment variables, ports, volumes, etc., as needed.

## Note

- By default, the `data.sql` and `schema.sql` files will be used for schema and data initialization.
- To disable initialization, override the respective properties with empty strings in the Docker Compose file.

- **Build the Application**:
1.**Run the Application**:
 Run the [build_and_run.sh](build_and_run.sh) script to build and run the application:
   ```bash
   ./build_and_run.sh
   ```
2**docker-compose up**:
   Run the following command to start the application:
   ```bash
   docker-compose up
   ```
3**Access the Application**:
    Once the application is running, you can access the API endpoints using Postman or any other API testing tool.

## Schema (optional) By deafult it will be created by hibernate
Please execute the [schema.sql](src%2Fmain%2Fresources%2Fdb%2Fschema.sql)    file to create the database schema. The schema.sql file contains SQL queries to create the database tables.

## Insert Data into Database

Please execute the [data.sql](src%2Fmain%2Fresources%2Fdb%2Fdata.sql)  file to insert data into the database. The data.sql file contains SQL queries to insert sample data into the database tables.

## Postman Collection Import

Download [Postman_OpenAPI_collection.json](Postman_OpenAPI_collection.json) and follow the steps below to import the collection into Postman:
1. **Open Postman**:
   Open the Postman application on your computer.

2. **Click on Import**:
   Click on the "Import" button located near the top-left corner of the Postman interface.

3. **Select Import Type**:
   Choose "OpenAPI (formerly Swagger)" from the list of import types.

4. **Upload JSON File**:
   Click on "Choose Files" and select the OpenAPI JSON file from your computer.

5. **Configure Import Options**:
   Customize the import options based on your preferences, such as importing endpoints, definitions, examples, etc.

6. **Click on Import**:
   After selecting the import options, click on the "Import" button to initiate the import process.

7. **Review Imported Collection**:
   Once the import is complete, you'll see a new collection created in Postman based on the JSON file.

8. **Explore Endpoints**:
   Expand the imported collection to view its folders, requests, and associated data. You can now explore the endpoints, send requests, and test your API directly from Postman.

## Testing the Application

To test the application, you can use the provided Postman collection to interact with the API endpoints. The collection includes requests for fetching product details, checking product availability, placing orders, and managing orders.

## Conclusion

This ecommerce application provides a basic framework for managing products, processing orders, and tracking inventory. By integrating data from different sources and designing a suitable database schema, the application can effectively handle ecommerce operations. The provided Postman collection allows you to interact with the API endpoints and test the functionality of the application.

