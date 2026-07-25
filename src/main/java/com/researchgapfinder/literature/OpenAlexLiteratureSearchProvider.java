package com.researchgapfinder.literature;

import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Safe offline MVP adapter. A production deployment can replace this bean with
 * an OpenAlex HTTP adapter without changing the domain or validation workflow.
 */
@Component
public class OpenAlexLiteratureSearchProvider implements LiteratureSearchProvider {
    @Override
    public List<LiteratureRecord> search(String query) {
        return List.of();
    }
}
