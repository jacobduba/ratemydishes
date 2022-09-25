count = 0
        for i in nums:
            for j in nums:
                if i == j:
                    count += 1
            if count >= 2:
                return True
            else:
                count == 0

        return False
