import json
import psycopg2

conn = psycopg2.connect()

#Setting auto commit false
conn.autocommit = True

#Creating a cursor object using the cursor() method
cursor = conn.cursor()



with open('') as f:
    dict = json.load(f)

counter = 0
for item in dict['ns0:Apartments']['Apartment']:

    res = json.dumps(item, ensure_ascii=False)
    res = res.replace("'", "")

    queryString = "INSERT INTO estates (data) values('" + str(res) + "');"

    counter = counter+1
    print(str(counter) + ". row added\n")
    cursor.execute(queryString)


