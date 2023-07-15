### Running Instructions
You can run the project in 2 ways:
* Locally on your environment: Just a normal spring boot app, no special requirements except having a mysql database 
running at port 3306 and configuring the DB credentials in the .yml file
* Using Docker: run `docker compose up -d` inside the project's root directory

### Testing:
#### CRUD operations:
* Please use the samples provided in the postman file (in the repo at root directory) to run the
different scenarios. The app ships with some seed data that you might use

#### Automatic irrigation (sensor API): 
* You will have to check the logs and database to check the results.
* The sensor's API is a mock controller that return failed responses in case the plot code value contains 'ERROR', 
otherwise it will succeed. 
  * These codes are part of the seed data, so you don't have to insert them.
  
* To trigger processing slots, reset the start_time colum to current time. This will make the scheduler detect and process these slots
* Use the below queries to modify the slot times to simulate the success and error cases
  * Activate successful plots
  `update plot_irrigation_slot set start_time = now(), end_time = DATE_ADD(now(), INTERVAL 5 MINUTE), status='READY'
    where plot_id in (select id from plot where code like 'PLOT-SUCCESS%');`
  * Activate failed plots:
  `update plot_irrigation_slot set start_time = now(), end_time = DATE_ADD(now(), INTERVAL 5 MINUTE), status='READY'
    where plot_id in (select id from plot where code like 'PLOT-ERROR%');`

* Check the logs, `plot_irrigation_slot` table, and `irrigation_job_history` table for results.

#### Alerting:
* An email will be sent to a preconfigured mail upon a slot fails. 
* You have to set your own email that you wish to receive on in the docker-compose file: 
services.Irrigation-service.environment.ALERTING_EMAIL. The mail server sometimes doesn't work, so you may try multiple times if it fails

#### Predictions: 
* An API is available in the postman collection