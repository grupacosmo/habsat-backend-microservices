# habsat_backend mappings map
<strong>Add frame </strong><br>
post under /flightData-frame with flightData frame obj in body
<hr>
<strong>Add frames </strong><br>
post under /flightData-frame/list with flightData frame objs in json-arr in body
<hr>
<strong>Change whole frame </strong><br>
put under /flightData-frame/{id} with new flightData-frame obj in body, or eventually fields which would like to be changed. Nullable values or their lack in body will not throw error.
<hr>
<strong>Get frames </strong><br>
get under /flightData-frame
<hr>
<strong>Get frame </strong><br>
get under /flightData-frame/{id} with id as path variable
<hr>
<strong>Delete frames</strong><br>
delete under /flightData-frame/~~deleteAll__webdev__access_265
<hr>
<strong>Delete frame by id</strong><br>
delete under /~~deleteAll__webdev__access_265/{id} with id as path variable
<hr>
<strong>Psql</strong><br>
user: habsat<br>
pass: habsat
<hr>
