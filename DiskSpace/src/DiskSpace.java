import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiskSpace
{
    public static void main(String[] args)
    {
        System.out.println("Case 1: " + isWritable(1, 1, null));
        System.out.println("Case 1: " + isWritable(1, 1, new HashSet<>()));
        System.out.println("Case 2: " + isWritable(1, 1, Stream.of(1).collect(Collectors.toCollection(HashSet::new))));
        System.out.println("Case 3: " + isWritable(4, 2, Stream.of(1, 4).collect(Collectors.toCollection(HashSet::new))));
        System.out.println("Case 4: " + isWritable(10, 2, Stream.of(2, 3, 5, 7).collect(Collectors.toCollection(HashSet::new))));
        System.out.println("Case 4: " + isWritable(10, 2, Stream.of(2, 3, 5, 7, 9, null, null).collect(Collectors.toCollection(HashSet::new))));
    }

    private static boolean isWritable(int blockSize, int fileSize, Set<Integer> occupiedSectors)
    {
        // Filter out obvious cases
        if (fileSize > blockSize)
        {
            return false;
        }

        if (occupiedSectors == null || occupiedSectors.isEmpty())
        {
            return true;
        }

        // Sort and remove duplicates
        List<Integer> sortedSectors = occupiedSectors.stream()
            .filter(Objects::nonNull)
            .sorted()
            .distinct()
            .collect(Collectors.toList());

        //Check beginning and end of the disk for free space
        if (sortedSectors.get(0) - 1 >= fileSize
            || (sortedSectors.get(sortedSectors.size()-1) != blockSize
            && blockSize - sortedSectors.get(sortedSectors.size()-1) >= fileSize))
        {
            return true;
        }

        // Check space in between
        for (int i = 0; i < sortedSectors.size(); i++)
        {
            if (i + 1 > sortedSectors.size() - 1)
            {
                break;
            }

            if (sortedSectors.get(i + 1) - sortedSectors.get(i) - 1 >= fileSize)
            {
                return true;
            }
        }

        return false;
    }

}
