# Distributed-Computing-Tasks Question I
## 
replacing the following code 
```
val joinedRdd: RDD[(Long, String, Int, String)] = rankedRdd
      .map { case (geoLocOid, itemName, itemRank) =>
        // Reformat rankedRdd to have geoLocOid as the key and (itemName, itemRank) as the value
        (geoLocOid, (itemName, itemRank))
      }
      .join(rddB) // Perform the join on geoLocOid and geographical_location_oid
      .map {
        case (geoLocOid, ((itemName, itemRank), geoLoc)) =>
          // After join, the structure is: (geoLocOid, ((itemName, itemRank), geoLoc))
          // We return a tuple with geoLocOid, itemName, itemRank, and geoLoc
          ( itemName, itemRank, geoLoc)
      }
```

to 

 
```
//Adding in random value in new col (new key) to ensure workload split equally and repartition it
val saltedRddA =  rankedRdd.map {
      case (geoLocOid, itemName, itemRank) =>
        val randomSalt = Random.nextInt(10)  
        ((geoLocOid, randomSalt), (itemName, itemRank)) 
    }

val saltedRddB = rddB.map {
      case (geoLocOid, geoLoc) =>
        val randomSalt = Random.nextInt(10)  
        ((geoLocOid, randomSalt), geoLoc)  
    }

// randomSalt and geoLocOid is the primary key use to join the table 
val joinedRdd = saltedRddA
      .join(saltedRddB)  // Join on the composite key (geoLocOid, randomSalt)
      .map {
        case ((geoLocOid, randomSalt), ((itemName, itemRank), geoLoc)) =>
          (itemName, itemRank, geoLoc) 
      }

// repartition the data 
val repartitionedRdd = joinedRdd.repartition(100)

```

