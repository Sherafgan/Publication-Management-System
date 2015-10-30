package Main.Model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Model {
    //UUID createPost(String title, String content, List<String> categories);
    //UUID createComment(UUID post, String author, String content);
    //List<Publication> getAllPublications();
    Optional<Publication> getPublicationOn(String title);
    List<Participant> getAllParticipantsOn(String name);
    //boolean existPost(UUID post);

    //Optional<Post> getPost(UUID uuid);

    //void updatePost(Post post);

    //void deletePost(UUID uuid);
}