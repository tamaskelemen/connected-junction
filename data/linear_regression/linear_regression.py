import csv
import sklearn
import pandas as pd
import numpy as np
i = 0
ret = []
with open('regions.csv', encoding='utf8') as file:
    rows = csv.reader(file)
    header = []
    for row in rows:
        if i is 0:
            header = row
            i = i +1
            continue
        dataset = pd.DataFrame([header, row], index = ['year', 'data'])
        X = dataset.iloc[0, :].values
        y = dataset.iloc[1, :].values

        from sklearn.model_selection import train_test_split
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.5, random_state=6)

        X_train = X_train.reshape(-1, 1)
        X_test = X_test.reshape(-1, 1)
        y_train = y_train.reshape(-1, 1)
        y_test = y_test.reshape(-1, 1)
        
        from sklearn.linear_model import LinearRegression
        regressor = LinearRegression()
        regressor.fit(X_train, y_train)
        y_pred = regressor.predict(X_test)
        avg = 0.0
        for t in y_pred.tolist():
            avg += float(t[0])
        avg = avg / len(y_pred.tolist())
        ret.append(avg)

tw = [float(i)/sum(ret) for i in ret]

with open('regression.csv', 'w+', newline='', encoding='utf8') as output:
    for elem in ret:
        output.write(str(elem)+'\n')

