"""
    Dynamic Programming solution to MinEditDistance
"""

def minEditDistance(s1, s2):
    """Compute minimum edit distance converting s1 -> s2."""

    len1 = len(s1)
    len2 = len(s2)

    # Create two-dimensional structure such that m[i][j] = 0
    # for i in 0 .. len1 and for j in 0 .. len2
    m = [None] * (len1 + 1)
    for i in range(len1+1):
        m[i] = [0] * (len2+1)

    # set up initial costs on horizontal and vertical
    for i in range(1, len1+1):
        m[i][0] = i
    for j in range(1, len2+1):
        m[0][j] = j

    # compute best 
    for i in range(1,len1+1):
        for j in range(1,len2+1):
            cost = 1
            if s1[i-1] == s2[j-1]: cost = 0

            replaceCost = m[i-1][j-1] + cost
            removeCost  = m[i-1][j] + 1
            insertCost  = m[i][j-1] + 1
            m[i][j]     = min(replaceCost,removeCost,insertCost)

    return m[len1][len2]

if __name__ == '__main__':
    print (minEditDistance('GCTAC', 'CTCA'), "is minimum distance")
    