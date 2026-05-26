package me.sirrahmas.ndcmap.data;

// Record in the database for an area on the map.
public class CollegeEntranceArea extends AbstractAreaRecord {

    public CollegeEntranceArea() {
        super(
            "College Entrance",
            // Corners for "Reception": 737, 4599; 737, 4825
            new Integer[] {238, 737, 737, 926, 926, 238},
            new Integer[] {4599, 4599, 4825, 4825, 5725, 5725}
        );
    }
}