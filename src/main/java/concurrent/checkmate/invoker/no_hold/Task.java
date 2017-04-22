package concurrent.checkmate.invoker.no_hold;

import action.candidate.Candidate;
import concurrent.checker.invoker.Pair;
import core.mino.Block;
import misc.ComparablePieces;
import searcher.checkmate.Checkmate;
import searcher.common.Result;
import searcher.common.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

class Task implements Callable<List<Pair<List<Block>, List<Result>>>> {
    private final Obj obj;
    private final List<ComparablePieces> targets;

    Task(Obj obj, List<ComparablePieces> targets) {
        this.obj = obj;
        this.targets = targets;
    }

    @Override
    public List<Pair<List<Block>, List<Result>>> call() throws Exception {
        Checkmate<Action> checkmate = obj.checkmateThreadLocal.get();
        Candidate<Action> candidate = obj.candidateThreadLocal.get();

        // 探索
        List<Pair<List<Block>, List<Result>>> allResults = new ArrayList<>();
        for (ComparablePieces piece : targets) {
            List<Block> blocks = piece.getBlocks();
            List<Result> results = checkmate.search(obj.field, blocks, candidate, obj.maxClearLine, obj.maxDepth);
            allResults.add(new Pair<>(blocks, results));
        }

        return allResults;
    }
}