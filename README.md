# Realm-Coursera
Project to try realm with android.

Problems faced while using realm.

1. Cant pass realm objects across threads. So after an async callback we need to perform read query on the ui thread.
2. Insertion in realm is a big pain for flat jsons.In the coursera example we need to first insert instructors and organization
    and then only we can insert course record in db.
3. LIKE operator not present for querying which makes code complex.

    
