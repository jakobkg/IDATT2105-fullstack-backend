package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Bookmark;
import edu.ntnu.jakobkg.idatt2105projbackend.model.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

    // Returns a list of bookmarks saved by a specified user
    List<Bookmark> findByBookmarkIdUserId(int userId);
}
