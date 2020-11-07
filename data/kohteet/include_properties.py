import json
import psycopg2

conn = psycopg2.connect(
   database="junction", user='postgres', password='asdasd', host='35.241.209.158', port= '5432'
)
#Setting auto commit false
conn.autocommit = True

#Creating a cursor object using the cursor() method
cursor = conn.cursor()



with open('Kohteet.json') as f:
    dict = json.load(f)

counter = 0
for item in dict['ns0:Apartments']['Apartment']:

    res = json.dumps(item, ensure_ascii=False)

    queryString = "INSERT INTO estates (data) values('" + str(res) + "');"

    counter = counter+1
    print(counter + ". row added\n")
    cursor.execute(queryString)


