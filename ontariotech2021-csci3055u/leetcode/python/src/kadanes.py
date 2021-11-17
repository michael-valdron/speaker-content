def kadanes_algorithm(array):
    if len(array) == 0:
        return 0
    
    running_sum = array[0]
    max_sum = array[0]
    
    for value in array[1:]:
        running_sum = max(value, running_sum + value)
        max_sum = max(max_sum, running_sum)
    
    return max_sum

# kadanes_algorithm([-5, 4, 1, -6, 5, -1, 4, 3, 2])
# => 13 ([5, -1, 4, 3, 2])
