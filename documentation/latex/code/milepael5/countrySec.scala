val thisTemp = PDF.coalesce(1).select(col("country"), 
 expr("(costPlusRentI / 100) * gdp as RealCost ")).sort(desc("RealCost"))