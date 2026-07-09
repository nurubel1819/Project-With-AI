# What is a Window Function?

Window functions were first introduced to standard SQL in 2003. Per the [PostgresSQL documentation](https://www.postgresql.org/docs/9.1/tutorial-window.html):

> “A _window function_ performs a calculation across a set of table rows that are somehow related to the current row…Behind the scenes, the window function is able to access more than just the current row of the query result.”

Window functions are similar to the aggregation done in the GROUP BY clause. However, rows are not grouped into a single row, each row retains their separate identity. That is, a window function may return a single value for each row. Here’s a good visualization of what I mean by that.

![](https://miro.medium.com/v2/resize:fit:1400/1*bWw3tHCsXRHyOKOkm81eUg.png)

Image by Author

Notice how the GROUP BY aggregation on the left hand side of the picture groups the three rows into one single row. The window function on the right hand side of the picture is able to output each row with an aggregation value. This may save you from having to do a join after the GROUP BY.

## Example: GROUP BY versus Window Function

Here’s a quick example to give you a taste of what a window function does.

Let’s say we have some salary data and we want to find to create a column that gives us the average salary for each job title.

![](https://miro.medium.com/v2/resize:fit:508/1*WAzUYHQeF7l4cRYT6mdBAQ.png)

Example Salary Data — Image by Author

![](https://miro.medium.com/v2/resize:fit:1400/1*WNjN_3P3JOz1h_o2tBQnaA.png)

Group By versus Window Function — Image by Author

On the left is what a `GROUP BY` aggregation would return and on the right is what a window function would return. As you can see, the group by consolidates our data into just three rows. With a window function, we retain the original 11 rows and have a new column called AVG_SALARY. We could then choose to compare each individual’s salary to the average salary if desired.

# Why use Window Functions?

==One major advantage of window functions is that it allows you to work with both aggregate and non-aggregate values all at once because the rows are not collapsed together.==

Window functions are also simple to use and read. That is, they can reduce the complexity of your queries, which makes it easier to maintain down the road.

In addition, they can help with performance issues. For example, you can use a window function instead of having to do a self-join or cross-join.

I promise, window functions are truly amazing and great to know.

![](https://miro.medium.com/v2/resize:fit:1400/0*Jeq2T-cphOxQBY-y)

Photo by [Brett Jordan](https://unsplash.com/@brett_jordan?utm_source=medium&utm_medium=referral) on [Unsplash](https://unsplash.com/?utm_source=medium&utm_medium=referral)

# Important Note:

Before we start, it is important to note that in terms of the order of operations in SQL, window functions come in sixth on the list.

![](https://miro.medium.com/v2/resize:fit:1400/1*MCyFDF35OZ3n0dyZqX6_Tw.png)

Image by Author

This is important because based off of this logical order, window functions are allowed in `SELECT` and `ORDER BY,` but they are not allowed in `FROM`, `WHERE`, `GROUP BY`, or `HAVING` clauses.

> _Note: If you really need to have it inside a_`_WHERE_` _clause or_ `_GROUP BY_` _clause, you may get around this limitation by using a subquery or a_ `_WITH_` _query._

# Window Function Syntax

Here’s what the generic syntax looks like for a window function in the `SELECT` clause.

![](https://miro.medium.com/v2/resize:fit:1080/1*NKUDO4a5fu5kVW-OlkHUJw.png)

Image by Author

There’s a lot of words here, so let’s look at some definitions:

- **window_function** is the name of the window function we want to use; for example, sum, avg, or row_number (we’ll learn more about these later)
- **expression** is the name of the column that we want the window function operated on. This may not be necessary depending on what window_function is used
- **OVER** is just to signify that this is a window function
- **PARTITION BY** divides the rows into partitions so we can specify which rows to use to compute the window function
- **partition_list** is the name of the column(s) we want to partition by
- **ORDER BY** is used so that we can order the rows within each partition. This is optional and does not have to be specified
- **order_list** is the name of the column(s) we want to order by
- **ROWS** can be used if we want to further limit the rows within our partition. This is optional and usually not used
- **frame_clause** defines how much to offset from our current row

Don’t worry about memorizing the definitions and syntax or even fully understanding what it means exactly right now. Everything will make a lot more sense once you look at the examples in the article and get an intuitive understanding of how to go about writing a window function.

## Quick Example

To help you get a better idea of how the syntax really works, below is an example of what a window function would look like in practice.

This is the query that would have generated the output we saw earlier regarding salary by job title.

![](https://miro.medium.com/v2/resize:fit:1400/1*GVX7Ws5Dk_f9-a6o9Wy_kQ.png)

Here, **AVG()** is the name of the window function, **SALARY** is the expression and **JOB_TITLE** is our partition list. We did not use an ORDER BY as it is not needed and we do not want to use ROWS because we do not want to further limit our partition.

![](https://miro.medium.com/v2/resize:fit:820/1*rUXfOhUaJCSMQkGh5FnEmA.png)

Image by Author

Again, no need for memorizing syntax for now. At this stage, the one concept I want you to understand is that the window function computes a value for each row in the “window” or “partition”. A window can be one of more rows and it is specified by the clause `PARTITION BY`. In our example, we partitioned by job title. As you can see in the snippet above, I’ve highlighted each job title a different color. Each color represents a different “window” or a different “partition”. The window function computes one average salary value for each partition.

# **List of Window Functions**

Now that you know the syntax, let’s take look at the different kinds of window functions that can be substituted in place of the red font below.

![](https://miro.medium.com/v2/resize:fit:1400/1*NV9bCjSJTKXQ8_-X-aHjVg.png)

Image by Author

There are three main types of window functions available to use: aggregate, ranking, and value functions. In the image below, you can see some of the names of the functions that fall within each group.

![](https://miro.medium.com/v2/resize:fit:1400/1*u8SPxze2b1OdCdPo1maVGw.png)

Image by Author

Here’s a quick overview of what each type of window function is useful for.

**Aggregate functions:** we can use these functions to calculate various aggregations such as average, total # of rows, maximum or minimum values, or total sum within each window or partition.

**Ranking functions:** these functions are useful for ranking rows within its partition.

==**Value functions**==**:** these functions allow you to compare values from previous or following rows within the partition or the first or last value within the partition.

# **Window Function Exercises**

Now let’s start doing some fun exercises to help you really grasp how window functions work. We’ll go through various exercises on aggregate, ranking, and value functions.

## Data

For the example problems below, I am using data from the Northwind database located on [this website](https://github.com/jpwhite3/northwind-SQLite3). See Northwind_small.sqlite. The file can also be found in my github repo.

> _Per the download source, “The Northwind sample database was provided with Microsoft Access as a tutorial schema for managing small business customers, orders, inventory, purchasing, suppliers, shipping, and employees. Northwind is an excellent tutorial schema for a small-business ERP, with customers, orders, inventory, purchasing, suppliers, shipping, employees, and single-entry accounting.”_

The full schema of the database is displayed on the website linked above. For the examples in this article, I will only be using the [Order] and [OrderDetail] tables.

![](https://miro.medium.com/v2/resize:fit:652/1*vz3DggeLfupObUnYlCa11A.png)

Source: [https://github.com/jpwhite3/northwind-SQLite3](https://github.com/jpwhite3/northwind-SQLite3)

# Aggregate Functions

![](https://miro.medium.com/v2/resize:fit:1400/1*zR68gv0uhJkq9B9PoUNDSA.png)

Image by Author

## Exercise 1: Create a new column that calculates average Unit Price for each CustomerId

![](https://miro.medium.com/v2/resize:fit:420/1*Mk6G_IyrlcgIS2cPBPhsTA.png)

Image by Author

From the list of aggregate window functions listed on the left, we can see that AVG() will be the window function that we want to use. Our expression will be the Unit Price column because we want to calculate the average of Unit Price.

Next, we need to figure out how we want to partition. That is, how should the rows be grouped together in order to create our partitions? The exercise statement tells us to find the avg price for each CustomerId. That tells us that we want to group rows that have the same CustomerId, so that is will be part of our partition list. For the purpose of this exercise, we have no use of an ORDER BY. Below is what our query would look like.

SELECT CustomerId,   
       UnitPrice,   
       **AVG(UnitPrice) OVER (PARTITION BY CustomerId) AS “AvgUnitPrice”**  
FROM [Order]   
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId

![](https://miro.medium.com/v2/resize:fit:828/1*c9eFD04nSDvxmzg2zza8oA.png)

Image by Author

As you can see in the image above, an average unit price is computed for each of our partitions of CustomerId.

Here’s some exercises to try for yourself:

1. Create a new column that calculates max Discount for each CustomerId
2. Create a new column that calculates min Unit Price for each ProductId

## Exercise 2: Create a new column that calculates average Unit Price for each group of CustomerId AND EmployeeId.

You can choose to partition by more than 1 column. Earlier, we calculated the average unit price for each CustomerId group. This time, we’ll add in EmployeeId.

SELECT CustomerId,   
       EmployeeId,   
       AVG(UnitPrice) OVER (**PARTITION BY CustomerId, EmployeeId**) AS       “AvgUnitPrice”  
FROM [Order]   
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId 

![](https://miro.medium.com/v2/resize:fit:736/1*NEoVEmqBzFSlX3NHNK9dUg.png)

Image by Author

Notice how the partition changes from earlier. The calculations are computed for every unique group of CustomerId and EmployeeId as visually shown by the different colors in the table.

# Ranking Functions

![](https://miro.medium.com/v2/resize:fit:1400/1*zR68gv0uhJkq9B9PoUNDSA.png)

Image by Author

## Exercise 3: Create a new column that ranks Unit Price in descending order for each CustomerId.

![](https://miro.medium.com/v2/resize:fit:676/1*GJU1s-fu2g8Y3DyTu6gF7g.png)

We can complete this exercise in three different ways.

We’ll use the first three ranking functions on the list on the left: ROW_NUMBER, RANK, and DENSE_RANK.

Each one has a slightly different way of ranking the data.

## ROW_NUMBER

We can use this function to show the row number of a given row within its partition. ==Note that for the ranking functions, we do not have to specify an expression within the parentheses like we did previously for the aggregate functions.==

In addition, since are doing a ranking, order is important here. We have to make sure that the Unit Price column is ordered correctly so that the ranking is applied correctly. To do so, we can add `**ORDER BY UnitPrice DESC**`as part of the windows function, right after `**PARTITION BY.**`

SELECT CustomerId,   
       OrderDate,   
       UnitPrice,   
       **ROW_NUMBER()** OVER (PARTITION BY CustomerId **ORDER BY UnitPrice DESC**) AS “UnitRank”  
FROM [Order]   
INNER JOIN OrderDetail   
ON [Order].Id = OrderDetail.OrderId

![](https://miro.medium.com/v2/resize:fit:1364/1*joUTr_rzCxsdei-4NM3O9A.png)

Source: Author

As you can see in the output above, our UnitPrice column is in descending order and the unit’s rank is shown for each customer id in the last column. There are 12 rows for customer ALFK, so the rank goes from 1 to 12.

You may be wondering, what happens if I use the ORDER BY at the end of the SQL statement and not inside the windows function, will I get the same results?

Take a minute a think about it and come back. Does it matter if I have the ORDER BY inside the windows function versus outside?

![](https://miro.medium.com/v2/resize:fit:1400/0*1uIAA69Tv28qCxIW)

Photo by [Markus Winkler](https://unsplash.com/@markuswinkler?utm_source=medium&utm_medium=referral) on [Unsplash](https://unsplash.com/?utm_source=medium&utm_medium=referral)

Let’s try it out! Remove the ORDER BY from the windows function and let’s add it to the end.

SELECT CustomerId,   
       OrderDate,   
       UnitPrice,      
       ROW_NUMBER() OVER (PARTITION BY CustomerId) AS “UnitRank”  
FROM [Order]  
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId  
**ORDER BY CustomerId, UnitPrice DESC** 

![](https://miro.medium.com/v2/resize:fit:1224/1*ZjC9zOWHR-p5GrVHAcI_tQ.png)

Hm, it looks like we aren’t getting the same results as earlier. Unit price is ordered correctly in descending order, but the unit’s rank doesn’t look right. Why not?

Remember back to the SQL order of operations. Window functions are processed sixth whereas the ORDER BY is processed tenth.

![](https://miro.medium.com/v2/resize:fit:1400/1*4geqKDCNEEdhvaxrJqdnjw.png)

Image by Author

So the row numbers were created BEFORE the UnitPrice was ordered. That’s why we don’t get the same results! Makes sense.

## RANK()

Now, let’s try RANK() in place of ROW_NUMBER().

SELECT CustomerId,   
       OrderDate,   
       UnitPrice,   
       **RANK()** OVER (PARTITION BY CustomerId ORDER BY UnitPrice DESC) AS “UnitRank”  
FROM [Order]   
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId

![](https://miro.medium.com/v2/resize:fit:1212/1*hjPb9avD8vWlbbwy35Eeqw.png)

Image by Author

What’ the difference now? With row number, there were no repeated numbers. But with RANK(), if you have multiple values with the exact same value, the rank function will give them the same rank.

Notice in rows 2 and 3, the unit price is 45.60, so both rows are given the same rank of 2. Rows 7 and 8 also have the same unit price and are given the same rank of 7.

Also note that the ranking skips a number. For example, in row 3, the rank skips to 4 since there are two rows of rank 2. If there were three rows with rank 2, then it would skip to rank 5 and so on.’

Well, what if you don’t want it to skip numbers? Well, we can use **DENSE_RANK()** instead.

## DENSE_RANK()

Again, replace our window function to be **DENSE_RANK()** and keep all else the same.

SELECT CustomerId,   
       OrderDate,   
       UnitPrice,   
       **DENSE_RANK()** OVER (PARTITION BY CustomerId ORDER BY UnitPrice DESC) AS “UnitRank”  
FROM [Order]   
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId

![](https://miro.medium.com/v2/resize:fit:1212/1*569nAYxQUvjSgeAG1SRS-A.png)

Image by Author

It follows the same behavior as RANK() in that if the values are the same, the same rank will be given to those rows. See rows 2 and 3. Notice that in row 4, the rank does not skip a number now. It is rank 3 instead of 4.

Your homework now is to learn how PERCENT_RANK() and NTILE() work and to try those functions for yourself.

# Value Functions

To me, value functions are probably the top reason why window functions are so amazing. These functions are great for extracting values from other rows that might be useful for a report.

![](https://miro.medium.com/v2/resize:fit:536/1*Fc1LZxxzIqoGpzQ2N3zdNA.png)

Image by Author

We can use the LAG or LEAD functions to help us create a column that is able to pull values from other rows. LAG can return values from the previous rows whereas LEAD returns values from following rows. Comparing previous or following rows can be super useful when working with time series data and calculating differences across time.

## Exercise 4: Create a new column that provides the previous order date’s Quantity for each ProductId.

SELECT ProductId,   
       OrderDate,   
       Quantity,   
       **LAG(Quantity) OVER (PARTITION BY ProductId ORDER BY OrderDate) AS "LAG"**  
FROM [Order]   
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId 

We use LAG on the Quantity column to return the value from the previous row. Just like before, we need to make sure our data is sorted in order inside our windows function. We’ll sort by the OrderDate here.

![](https://miro.medium.com/v2/resize:fit:1260/1*U5Pb6kyZYtMvbhHw13o3YA.png)

Image by Author

As you can see in the image above, we get a column with the previous OrderDate’s Quantity. This is really useful because we could compare the current order date to the previous order date and calculate the differences across the two time periods. In the first row, there is no previous order date, so it is NaN, or null.

## Exercise 5: Create a new column that provides the following order date’s Quantity for each ProductId.

This is going to look pretty similar to our earlier example. However, this time, since we want the following row, we will use LEAD().

SELECT ProductId,   
       OrderDate,   
       Quantity,   
       **LEAD**(Quantity) OVER (PARTITION BY ProductId ORDER BY OrderDate) AS "LEAD"  
FROM [Order]   
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId

![](https://miro.medium.com/v2/resize:fit:1240/1*iuOau5lUBTHwmPAbfngLlQ.png)

Image by Author

As you can see, the new column LEAD contains the values from the the next row down.

## Exercise 6: Create a new column that provides the very first Quantity ever ordered for each ProductId.

To get the first quantity, we can use the **FIRST_VALUE** function, which will give us the first value within a partition.

SELECT ProductId,   
       OrderDate,   
       Quantity,   
       **FIRST_VALUE**(Quantity) OVER (PARTITION BY ProductId ORDER BY OrderDate) AS "FirstValue"  
FROM [Order]   
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId

![](https://miro.medium.com/v2/resize:fit:536/1*crsYDwLQoWTRX1Ilrkwusw.png)

Image by Author

As you can see in the image, the first order for product ID 1 was on 8/20/2012 with a quantity of 45, so we get a value of 45 for all rows related to product 1.

Here’s an exercise for you to try yourself.

1. Create a new column that provides the second Quantity ordered for each ProductId. (HINT: use the NTH_VALUE function)

## Using the frame_clause

Let’s take a quick break from the exercises to learn a new concept that hasn’t been discussed yet.

![](https://miro.medium.com/v2/resize:fit:1400/0*Q3m6aliQHLibhxuD)

Photo by [Lubo Minar](https://unsplash.com/@bubo?utm_source=medium&utm_medium=referral) on [Unsplash](https://unsplash.com/?utm_source=medium&utm_medium=referral)

You may remember from the definitions at the beginning that we can specify ROWS with a frame_clause to further limit our window size. I’m saving this towards the end here because people tend to get a bit confused about this. I’m going to quickly go over the syntax and how it’s used, then let’s look at an example to really understand what’s going on.

Here’s what the generic syntax looks like

**ROWS BETWEEN** <starting_row> **AND** <ending_row>

In the <starting_row> and <ending row>, we have the following options at our disposal:

- UNBOUNDED PRECEDING — all rows before the current row in the partition, i.e. the first row of the partition
- [some #] PRECEDING — # of rows before the current row
- CURRENT ROW — the current row
- [some #] FOLLOWING — # of rows after the current row
- UNBOUNDED FOLLOWING — all rows after the current row in the partition, i.e. the last row of the partition

Here’s some examples of how it could be written:

- ROWS BETWEEN 3 PRECEDING AND CURRENT ROW — this means look back the previous 3 rows up to the current row.
- ROWS BETWEEN UNBOUNDED PRECEDING AND 1 FOLLOWING — this means look from the first row of the partition to 1 row after the current row
- ROWS BETWEEN 5 PRECEDING AND 1 PRECEDING — this means look back the previous 5 rows up to 1 row before the current row
- ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING — this means look from the first row of the partition to the last row of the partition

One worthy note is that anytime that you add an ORDER BY clause, SQL sets the default window as ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW.

## Exercise 7: Calculate a cumulative moving average UnitPrice for each CustomerId.

In order to calculate a cumulative moving average, we will take advantage of the frame_clause.

SELECT CustomerId,   
       UnitPrice,   
       AVG(UnitPrice) OVER (PARTITION BY CustomerId   
       ORDER BY CustomerId   
       **ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW**) AS “CumAvg”  
FROM [Order]  
INNER JOIN OrderDetail ON [Order].Id = OrderDetail.OrderId

![](https://miro.medium.com/v2/resize:fit:472/1*FK8pEUSQhqKZv4FGNekZFA.png)

Image by Author

In the output above, you can see that an average is recalculated at every row. In row 1, there is only 1 number, so the average is 45.60. In row 2, the CumAvg is the average of 45.60 and 18. In row 3, the CumAvg is the average of 45.60, 18, and 12. And so on…

Here’s some exercises to try for yourself:

1. Calculate the average Quantity of the previous 5 rows up to the previous 3 rows for each CustomerId.
2. Calculated the minimum UnitPrice for the previous 2 rows up to the current row for each CustomerId

![](https://miro.medium.com/v2/resize:fit:1400/0*o_ryS1rinojY4jmO)

Photo by [Jingda Chen](https://unsplash.com/@jingdachen?utm_source=medium&utm_medium=referral) on [Unsplash](https://unsplash.com/?utm_source=medium&utm_medium=referral)

If you’re up for a **challenge**, try this bonus one.

**Challenge Exercise:** Create a new column that provides the last Quantity ordered for each ProductId. (Hint: use the LAST_VALUE() window function and think about the partitioning)

Great work getting through this article! Window functions are really powerful and a lot of fun, so I hope you enjoyed going through these exercises. Thank you for reading! Drop a comment below if you have feedback or questions.