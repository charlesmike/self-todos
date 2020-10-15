/**
 * Copyright (c) 2020, Self XDSD Contributors
 * All rights reserved.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to read the Software only. Permission is hereby NOT GRANTED to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.selfxdsd.todos;

import com.selfxdsd.api.Project;
import com.selfxdsd.api.Self;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Puzzles REST Controller.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 * @todo #16:30min Once we have Issues.search(...) method in self-core,
 *  we should search for the Issues with label "puzzle", we are only
 *  interested in those (at the moment all the Issues are being used).
 */
@RestController
public class PuzzlesApi {

    /**
     * Self's core.
     */
    private final Self selfCore;

    /**
     * Puzzles Component.
     */
    private final PuzzlesComponent puzzlesComponent;

    /**
     * Ctor.
     * @param selfCode Self Core, injected by Spring automatically.
     * @param puzzlesComponent Puzzles Component.
     */
    @Autowired
    public PuzzlesApi(
        final Self selfCode,
        final PuzzlesComponent puzzlesComponent
    ) {
        this.selfCore = selfCode;
        this.puzzlesComponent = puzzlesComponent;
    }

    /**
     * Trigger the reading/processing of the puzzles for the given Project.
     * @param provider Provider name (github, gitlab etc).
     * @param owner Owner login (user or organization name).
     * @param name Simple name of the repository.
     * @return Response OK.
     * @throws PuzzlesProcessingException Something went wrong during reading
     *  puzzles.
     */
    @GetMapping(value = "/pdd/{provider}/{owner}/{name}")
    public ResponseEntity<String> readPuzzles(
        @PathVariable final String provider,
        @PathVariable final String owner,
        @PathVariable final String name
    ) throws PuzzlesProcessingException {
        final ResponseEntity<String> resp;
        final Project project = this.selfCore.projects().getProjectById(
            owner + "/" + name, provider
        );
        if (project == null) {
            resp = ResponseEntity.badRequest().build();
        } else {
            this.puzzlesComponent.review(project);
            resp = ResponseEntity.ok().build();
        }
        return resp;
    }
}
