# Distributed-Computing-Tasks Question H 
## considerations when designing the code
1) Write a code that do not need edition in the object file by doing the following: 
	i taking in array input that contain the path to datset in the following order
		1. dataset A
		2. dataset B 
		3. dataset C 
		4. limit_x : identify the top X items 

2) Try to reduce the data movement by 
	1. Obtaining and converting the requried col into RDD on line 26
	2. Counting the item_name and dense_rank in rddA 
	3. Using item_Rank, item_name and geoLocOid to joing with rddB
	4. limit the result before writing into dataset C (output file) 

3) Movement of the data in order to ensure the result map correctly 

4) Setting up the version for spark and scala in properties for easy access 

5) Monitor the memory usage and adjust the size of RDD partitions if needed. This can be done using spark.executor.memory or cache() can apply to dataset. 

6) Did dataset A keys have disproportionately large amounts of data that casue an imbalance in processing and leading to some nodes overwhelmed. If yes, have to re map the key and value. 

## some of the spark configurations that can be consider: 
1. memoryOverhead 
	1. --conf spark.yarn.executor.memoryOverhead or --conf spark.executor.memoryOverhead depend on the version use. 
	3. --conf spark.driver.memoryOverhead
2. --conf spark.sql.shuffle.partitions
3. --conf spark.default.parallelism
4. --conf spark.sql.legacy.fallbackBaseUnBase64
5. --conf spark.local.dir
6. --conf spark.sql.files.maxPartitionBytes
7. --conf spark.dynamicAllocation


