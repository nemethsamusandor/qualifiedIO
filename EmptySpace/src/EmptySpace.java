import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmptySpace
{
    public static void main(String[] args)
    {
        System.out.println("Case 1: " + isWritable(1, 1, new HashSet<>()));
        System.out.println("Case 2: " + isWritable(1, 1, Stream.of(1).collect(Collectors.toCollection(HashSet::new))));
        System.out.println("Case 3: " + isWritable(4, 2, Stream.of(1, 4).collect(Collectors.toCollection(HashSet::new))));

    }

    private static boolean isWritable(int blockSize, int fileSize, Set<Integer> occupiedSectors)
    {
        if (fileSize > blockSize || occupiedSectors == null)
        {
            return false;
        }

        if (occupiedSectors.isEmpty())
        {
            return true;
        }

        List<Integer> sortedSectors = occupiedSectors.stream().sorted().collect(Collectors.toList());

        for (int i = 0; i < sortedSectors.size(); i++)
        {
            Integer nextItem = (i + 1 > sortedSectors.size() - 1) ? blockSize : sortedSectors.get(i + 1);

            int freeSpace = nextItem - sortedSectors.get(i);

            if (freeSpace >= fileSize)
            {
                return true;
            }
        }

        return false;
    }

}
