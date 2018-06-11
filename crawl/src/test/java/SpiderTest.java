import com.chinamcloud.spider.Spider;
import com.chinamcloud.spider.scheduler.RedisQueueScheduler;
import com.chinamcloud.spider.scheduler.RedisSetDuplicateRemover;
import com.chinamcloud.spider.scheduler.core.DuplicateRemoverScheduler;

public class SpiderTest {

    public static void main(String[] args) {
        Spider spider = new Spider(4);
        DuplicateRemoverScheduler duplicateRemoverScheduler
                = DuplicateRemoverScheduler.get()
                .setScheduler(new RedisQueueScheduler())
                .setDuplicateRemover(new RedisSetDuplicateRemover());
        spider.setScheduler(duplicateRemoverScheduler);
        spider.run();
    }
}
