"""
    Dynamic Programming solution to MinEditDistance. Also produces the operations.
"""
REPLACE = 0
REMOVE  = 1
INSERT  = 2

def minEditDistance(s1, s2):
    """Compute minimum edit distance converting s1 -> s2 with operations."""
    len1 = len(s1)
    len2 = len(s2)

    # Create two-dimensional structure such that m[i][j] = 0
    # for i in 0 .. len1 and for j in 0 .. len2
    m = [None] * (len1 + 1)
    op = [None] * (len1 + 1)
    for i in range(len1+1):
        m[i] = [0] * (len2+1)
        op[i] = [-1] * (len2+1)

    # set up initial costs on horizontal and vertical
    for j in range(1, len2+1):
        m[0][j] = j
    for i in range(1, len1+1):
        m[i][0] = i

    # compute best 
    for i in range(1,len1+1):
        for j in range(1,len2+1):
            cost = 1
            if s1[i-1] == s2[j-1]: cost = 0

            replaceCost = m[i-1][j-1] + cost
            removeCost  = m[i-1][j] + 1
            insertCost  = m[i][j-1] + 1
            costs       = [replaceCost,removeCost,insertCost]
            m[i][j]     = min(costs)
            op[i][j]    = costs.index(m[i][j])

    ops = []
    i = len1
    j = len2
    while i != 0 or j != 0:
        if op[i][j] == REMOVE or j == 0:
            ops.append('remove {}-th char {} of {},'.format(i,s1[i-1],s1))
            i = i-1
        elif op[i][j] == INSERT or i == 0:
            ops.append('insert {}-th char {} of {},'.format(j,s2[j-1],s2))
            j = j-1
        else:
            if m[i-1][j-1] < m[i][j]:
                ops.append('replace {}-th char of {} ({}) with {},'.format(i,s1,s1[i-1],s2[j-1]))
            i,j = i-1,j-1
    
    return m[len1][len2], ops

if __name__ == '__main__':
    result = minEditDistance('GCTAC', 'CTCA')
    print (result)
    print (result[0], "is minimum distance")
    for op in result[1]:
        print (op)
    