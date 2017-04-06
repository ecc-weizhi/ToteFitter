# ToteFitter

A O(VN + NLog(N)) solution to solve a programming puzzle using dynamic programming where V is the volume of tote and N is the number of products input.

## Problem statement:
Given a tote that is 45 centimeters long, 30 wide and 35 high, find a configuration of products that can fit in the tote such that the price value of the configuration is maximize. If there are multiple solution with same total price value, pick the solution with the smallest total weight. You can assume that if the products fit into the tote both individually and together by total volume, that you'll be able to find a way to pack them in. You can only take 1 of any product.

## Solution overview:
We first narrow down the input size by removing products which 
1. has a dimension that exceed tote dimension or
2. has volume that exceed tote volume.

We then further narrow down the inputsize by removing excess product of same volume but of lower price value. 
For example we have a tote with volume 5 and have the following 4 products:
- ProductA: volume 2, price 3
- ProductB: volume 2, price 4
- ProductC: volume 2, price 8
- ProductD: volume 2, price 10

We remove ProductA and ProductB because they will never be chosen in an optimal solution.

Finally we work out the optimal solution by finding the optimal solution for tote with volume V = 0, V = 1, V = 2, ..., V = 45 * 30 * 35.
For each V, we look at all previous optimal solution for preceding value of V and find a best product to be added to the tote. 
