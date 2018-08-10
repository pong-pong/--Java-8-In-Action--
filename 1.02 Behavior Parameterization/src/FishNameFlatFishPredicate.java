public class FishNameFlatFishPredicate implements FishPredicate {
    @Override
    public boolean test(Fish fish) {
        return "flatfish".equals(fish.getName());
    }
}
