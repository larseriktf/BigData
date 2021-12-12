collection.aggregate(Seq(
        Aggregates.group("$country",
        Accumulators.sum("score", "$gdp"),
        Accumulators.avg("average_taxes", "$taxRevenue")),
        
        Aggregates.sort(orderBy(descending("score"))),
        Aggregates.limit(10)
    ))
    .printResults()