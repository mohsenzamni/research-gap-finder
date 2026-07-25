package com.researchgapfinder.literature;

import java.util.List;

public interface LiteratureSearchProvider {
    List<LiteratureRecord> search(String query);
    record LiteratureRecord(String title, String abstractText, String sourceUrl) {}
}
